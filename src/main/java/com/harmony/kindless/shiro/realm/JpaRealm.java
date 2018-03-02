package com.harmony.kindless.shiro.realm;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.harmony.kindless.core.domain.Permission;
import com.harmony.kindless.core.domain.Role;
import com.harmony.kindless.core.domain.User;
import com.harmony.kindless.core.service.SecurityService;

/**
 * @author wuxii@foxmail.com
 */
public class JpaRealm extends AuthorizingRealm {

    public static final String REALM_NAME = "jpa";

    private SecurityService securityService;

    public JpaRealm() {
        this.setName(REALM_NAME);
    }

    public JpaRealm(SecurityService securityService) {
        this.setName(REALM_NAME);
        this.securityService = securityService;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken credentials = (UsernamePasswordToken) token;
        String username = credentials.getUsername();
        if (username == null) {
            throw new AuthenticationException("username not provided");
        }
        User user = securityService.findUser(username);
        if (user == null) {
            throw new AuthenticationException("user not found");
        }
        SimpleAuthenticationInfo authc = new SimpleAuthenticationInfo();
        SimplePrincipalCollection principals = new SimplePrincipalCollection();
        principals.add(username, REALM_NAME);
        authc.setPrincipals(principals);
        authc.setCredentials(user.getPassword());
        authc.setCredentialsSalt(ByteSource.Util.bytes(username));
        return authc;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authz = null;
        Collection realmPrincipals = principals.fromRealm(REALM_NAME);
        if (realmPrincipals != null && !realmPrincipals.isEmpty()) {
            authz = new SimpleAuthorizationInfo();
            String username = (String) realmPrincipals.iterator().next();
            User user = securityService.findUser(username);
            Set<String> roles = new LinkedHashSet<>(user.getRoles().size());
            Set<String> permissions = new LinkedHashSet<>();
            if (!user.getRoles().isEmpty()) {
                for (Role role : user.getRoles()) {
                    roles.add(role.getName());
                    for (Permission permission : role.getPermissions()) {
                        permissions.add(permission.getName());
                    }
                }
            }
            authz.setRoles(roles);
            authz.setStringPermissions(permissions);
        }
        return authz;
    }

    public SecurityService getSecurityService() {
        return securityService;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

}