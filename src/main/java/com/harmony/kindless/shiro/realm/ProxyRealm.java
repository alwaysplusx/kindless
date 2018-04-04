package com.harmony.kindless.shiro.realm;

import java.util.Set;

import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.harmony.kindless.jwt.JwtToken;
import com.harmony.kindless.jwt.JwtTokenVerifier;
import com.harmony.kindless.oauth.domain.AccessToken;
import com.harmony.kindless.oauth.domain.ScopeCode;
import com.harmony.kindless.oauth.service.AccessTokenService;
import com.harmony.kindless.shiro.PrimaryPrincipal;
import com.harmony.kindless.shiro.authc.JwtAuthenticationToken;

/**
 * 用户授权的realm
 * 
 * @author wuxii@foxmail.com
 */
public class ProxyRealm extends AbstractJwtRealm {

    public static final String REALM_PROXY = "proxy";
    private JwtTokenVerifier jwtTokenVerifier;
    private AccessTokenService accessTokenService;

    public ProxyRealm() {
        super(REALM_PROXY);
    }

    @Override
    protected boolean supports(JwtToken jwtToken) {
        return jwtToken.isUserProxy();// 来自用户授权的第三方代理
    }

    /*
     * 进行账号认证, 只需要返回token对应的认证账号
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(JwtAuthenticationToken authcToken) throws AuthenticationException {
        JwtToken jwtToken = authcToken.getJwtToken();

        AccessToken accessToken = accessTokenService.findByAccessToken(jwtToken.getToken());
        if (accessToken == null) {
            throw new AuthenticationException("invalid token");
        }

        String username = jwtToken.getUsername();

        SimplePrincipalCollection principals = new SimplePrincipalCollection();
        PrimaryPrincipal pp = SimplePrimaryPrincipal//
                .newBuilder()//
                .setUser(accessToken.getUser())//
                .setClient(accessToken.getClientInfo())//
                .setToken(jwtToken.getToken())//
                .setParam("scopes", getPermissions(accessToken.getScopeCode()))//
                .build();
        principals.add(pp, getName());

        ByteSource salt = ByteSource.Util.bytes(username);

        return new SimpleAuthenticationInfo(principals, accessToken.getAccessToken(), salt);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authz = null;
        PrimaryPrincipal pp = principals.oneByType(PrimaryPrincipal.class);
        if (pp != null) {
            authz = new SimpleAuthorizationInfo();
            Set<String> permissions = (Set<String>) pp.getParam("scopes");
            if (permissions == null || permissions.isEmpty()) {
                permissions = getPermissions(accessTokenService.findByAccessToken(pp.getToken()).getScopeCode());
                authz.setStringPermissions(permissions);
            }
        }
        return authz;
    }

    protected Set<String> getPermissions(ScopeCode scopeCode) {
        return OAuthUtils.decodeScopes(scopeCode.getScopes());
    }

    public JwtTokenVerifier getJwtTokenVerifier() {
        return jwtTokenVerifier;
    }

    public void setJwtTokenVerifier(JwtTokenVerifier jwtTokenVerifier) {
        this.jwtTokenVerifier = jwtTokenVerifier;
    }
}
