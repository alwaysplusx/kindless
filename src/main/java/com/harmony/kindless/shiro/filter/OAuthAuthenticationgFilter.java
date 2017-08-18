package com.harmony.kindless.shiro.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;

import com.harmony.kindless.oauth.domain.AccessToken;
import com.harmony.kindless.oauth.service.AccessTokenService;
import com.harmony.kindless.shiro.OAuthAccessToken;

/**
 * @author wuxii@foxmail.com
 */
public class OAuthAuthenticationgFilter extends AbstractTokenFilter {

    private static final String X_ACCESS_TOKEN = "X-AccessToken";

    @Autowired
    private AccessTokenService accessTokenService;

    public OAuthAuthenticationgFilter(AccessTokenService accessTokenService) {
        this.accessTokenService = accessTokenService;
    }

    @Override
    protected AuthenticationToken createToken(String token) {
        AccessToken accessToken = accessTokenService.findOne(token);
        return new OAuthAccessToken(accessToken.getUsername(), token);
    }

    @Override
    protected String getToken(HttpServletRequest request, HttpServletResponse response) {
        return request.getHeader(X_ACCESS_TOKEN);
    }

    public AccessTokenService getAccessTokenService() {
        return accessTokenService;
    }

    public void setAccessTokenService(AccessTokenService accessTokenService) {
        this.accessTokenService = accessTokenService;
    }

}
