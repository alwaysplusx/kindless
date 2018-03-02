package com.harmony.kindless.shiro.listener;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationListener;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.harmony.kindless.core.service.UserService;
import com.harmony.kindless.oauth.domain.AccessToken;
import com.harmony.kindless.oauth.service.AccessTokenService;

/**
 * @author wuxii@foxmail.com
 */
public class LoginListener implements AuthenticationListener {

    @Autowired
    private UserService userService;
    @Autowired
    private AccessTokenService accessTokenService;

    @Override
    public void onSuccess(AuthenticationToken token, AuthenticationInfo info) {
        userService.findByUsername((String) token.getPrincipal());
        // SecurityUtils.applyToSession(user);
        if (token instanceof AccessToken) {
            accessTokenService.findOne(((AccessToken) token).getAccessToken());
            // SecurityUtils.applyToSession(accessToken);
        }
    }

    @Override
    public void onFailure(AuthenticationToken token, AuthenticationException ae) {
    }

    @Override
    public void onLogout(PrincipalCollection principals) {
    }

}
