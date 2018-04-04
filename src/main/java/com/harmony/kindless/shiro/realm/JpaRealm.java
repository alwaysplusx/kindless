package com.harmony.kindless.shiro.realm;

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

import com.harmony.kindless.core.domain.User;
import com.harmony.kindless.shiro.AuthorizationService;
import com.harmony.kindless.shiro.AuthorizationService.AuthorizationCollection;
import com.harmony.kindless.shiro.PrimaryPrincipal;

/**
 * @author wuxii@foxmail.com
 */
public class JpaRealm extends AuthorizingRealm {

    public static final String REALM_JPA = "jpa";

    private AuthorizationService authorizationService;

    public JpaRealm() {
        this.setName(REALM_JPA);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upt = (UsernamePasswordToken) token;
        String username = upt.getUsername();
        if (username == null) {
            throw new AuthenticationException("username not provided");
        }
        User user = authorizationService.getUser(username);
        if (user == null) {
            throw new AuthenticationException("user not found");
        }

        SimplePrincipalCollection principals = new SimplePrincipalCollection();
        PrimaryPrincipal pp = SimplePrimaryPrincipal//
                .newBuilder()//
                .setUser(user)//
                .build();
        principals.add(pp, getName());

        ByteSource salt = ByteSource.Util.bytes(username);

        return new SimpleAuthenticationInfo(principals, user.getPassword(), salt);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authz = null;
        PrimaryPrincipal pp = principals.oneByType(PrimaryPrincipal.class);
        if (pp != null && pp.getUserId() != null && pp.getClientId() == null) {
            authz = new SimpleAuthorizationInfo();
            AuthorizationCollection ac = authorizationService.getAuthorizationCollection(pp.getUserId());
            authz.setRoles(ac.getRoles());
            authz.setStringPermissions(ac.getPermissions());
        }
        return authz;
    }

    public AuthorizationService getAuthorizationService() {
        return authorizationService;
    }

    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

}