package com.harmony.kindless.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.harmony.kindless.core.domain.User;
import com.harmony.kindless.jwt.JwtToken;
import com.harmony.kindless.jwt.JwtTokenService;
import com.harmony.kindless.shiro.AuthorizationService;
import com.harmony.kindless.shiro.AuthorizationService.AuthorizationCollection;
import com.harmony.kindless.shiro.PrimaryPrincipal;
import com.harmony.kindless.shiro.authc.JwtAuthenticationToken;

/**
 * @author wuxii@foxmail.com
 */
public class UserRealm extends AbstractJwtRealm {

    public static final String REALM_USER = "user";
    private AuthorizationService authorizationService;
    private JwtTokenService jwtTokenService;

    public UserRealm() {
        super(REALM_USER);
    }

    @Override
    protected boolean supports(JwtToken jwtToken) {
        return jwtToken.isUserOnly();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(JwtAuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = token.getJwtToken();
        if (!jwtTokenService.verify(jwtToken)) {
            throw new AuthenticationException("invalid token");
        }
        String username = jwtToken.getUsername();
        User user = authorizationService.getUser(username);

        SimplePrincipalCollection principals = new SimplePrincipalCollection();
        PrimaryPrincipal pp = SimplePrimaryPrincipal.newBuilder()//
                .setUser(user)//
                .setToken(jwtToken.getToken())//
                .build();
        principals.add(pp, getName());

        ByteSource salt = ByteSource.Util.bytes(username);

        return new SimpleAuthenticationInfo(principals, jwtToken.getToken(), salt);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authz = null;
        PrimaryPrincipal pp = principals.oneByType(PrimaryPrincipal.class);
        if (pp != null) {
            authz = new SimpleAuthorizationInfo();
            AuthorizationCollection ac = authorizationService.getAuthorizationCollection(pp.getUserId());
            authz.addRoles(ac.getRoles());
            authz.addStringPermissions(ac.getPermissions());
        }
        return authz;
    }

    public AuthorizationService getAuthorizationService() {
        return authorizationService;
    }

    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    public JwtTokenService getJwtTokenService() {
        return jwtTokenService;
    }

    public void setJwtTokenService(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

}
