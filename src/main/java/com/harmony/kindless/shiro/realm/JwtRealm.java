package com.harmony.kindless.shiro.realm;

import java.util.Collection;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.harmony.kindless.core.service.SecurityService;
import com.harmony.kindless.shiro.JwtToken;
import com.harmony.kindless.shiro.JwtToken.ThridpartPrincipal;
import com.harmony.kindless.shiro.authc.JwtAuthenticationToken;

/**
 * @author wuxii@foxmail.com
 */
public class JwtRealm extends AuthorizingRealm {

    public static final String REALM_NAME = "jwt";

    private SecurityService securityService;

    public JwtRealm() {
        this(null);
    }

    public JwtRealm(SecurityService securityService) {
        this.securityService = securityService;
        this.setName(REALM_NAME);
        this.setAuthenticationTokenClass(JwtAuthenticationToken.class);
    }

    /*
     * 进行账号认证, 只需要返回token对应的认证账号
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtAuthenticationToken authcToken = (JwtAuthenticationToken) token;
        if (!securityService.verify(authcToken.getJwtToken())) {
            throw new AuthenticationException("invalid token");
        }
        JwtToken jwtToken = authcToken.getJwtToken();
        SimpleAuthenticationInfo authc = new SimpleAuthenticationInfo();
        String username = jwtToken.getUsername();
        SimplePrincipalCollection principals = new SimplePrincipalCollection();
        ThridpartPrincipal tpp = jwtToken.geThridpartPrincipal();
        if (tpp == null) {
            principals.add(username, JpaRealm.REALM_NAME);
        } else {
            principals.add(username, JwtRealm.REALM_NAME);
            principals.add(tpp, JwtRealm.REALM_NAME);
        }
        authc.setPrincipals(principals);
        authc.setCredentials(jwtToken.getToken());
        authc.setCredentialsSalt(ByteSource.Util.bytes(username));
        return authc;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Collection<ThridpartPrincipal> tpps = principals.byType(ThridpartPrincipal.class);
        SimpleAuthorizationInfo authz = null;
        if (tpps != null && !tpps.isEmpty()) {
            // FIXME find tpp scope permission
            authz = new SimpleAuthorizationInfo();
            ThridpartPrincipal tpp = tpps.iterator().next();
            authz.addStringPermission(tpp.getScope());
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
