package com.harmony.kindless.shiro.realm;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.harmony.kindless.jwt.JwtToken;
import com.harmony.kindless.oauth.domain.AccessToken;
import com.harmony.kindless.oauth.service.AccessTokenService;
import com.harmony.kindless.shiro.PrimaryPrincipal;
import com.harmony.kindless.shiro.authc.JwtAuthenticationToken;

/**
 * @author wuxii@foxmail.com
 */
public class ClientRealm extends AbstractJwtRealm {

    public static final String REALM_CLIENT = "client";

    private AccessTokenService accessTokenService;

    public ClientRealm() {
        super(REALM_CLIENT);
    }

    @Override
    protected boolean supports(JwtToken jwtToken) {
        return jwtToken.isThirdPartOnly();// 仅第三方的访问
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(JwtAuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = token.getJwtToken();
        AccessToken accessToken = accessTokenService.findByAccessToken(jwtToken.getToken());
        if (accessToken == null) {
            throw new AuthenticationException("invalid token");
        }
        String username = jwtToken.getUsername();

        SimplePrincipalCollection principals = new SimplePrincipalCollection();
        PrimaryPrincipal pp = SimplePrimaryPrincipal//
                .newBuilder()//
                .setClient(accessToken.getClientInfo())//
                .setToken(jwtToken.getToken())//
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
            // TODO find scope codes, 通过#doGetAuthenticationInfo已经得出的数据来降低查询次数
        }
        return authz;
    }

    /**
     * 根据提供的access token查找客户端所拥有的权限
     * 
     * @param accessToken
     *            access token
     * @return permissions
     */
    protected Set<String> getPermissions(AccessToken accessToken) {
        return null;
    }

}
