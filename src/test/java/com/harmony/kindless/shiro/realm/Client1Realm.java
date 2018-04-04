package com.harmony.kindless.shiro.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 根据client的信息对client的权限等进行加载
 * 
 * @author wuxii@foxmail.com
 */
public class Client1Realm extends AuthorizingRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // AccessToken accessToken = accessTokenService.findById((String) token.getPrincipal());
        // if (accessToken != null) {
        // if (accessToken.isExpired()) {
        // throw new UnauthorizedException("acces_token is expired");
        // }
        // // oauth的账号顺序为 username, accessToken
        // List<String> principals = Arrays.asList(accessToken.getUsername(), accessToken.getAccessToken());
        // return new SimpleAuthenticationInfo(principals, accessToken.getAccessToken(), getName());
        // }
        // throw new AuthenticationException("access_token not found");
        return null;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        final SimpleAuthorizationInfo result = new SimpleAuthorizationInfo();

        List principalList = principals.asList();
        if (principalList.size() < 2) {
            throw new AuthenticationException("client info not found");
        }

        // AccessToken accessToken = accessTokenService.findById((String) principalList.get(1));
        // result.setStringPermissions(OAuthUtils.decodeScopes(accessToken.getScope()));

        return result;
    }

}
