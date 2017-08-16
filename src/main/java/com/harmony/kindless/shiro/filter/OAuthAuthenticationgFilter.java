package com.harmony.kindless.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.harmony.kindless.oauth.domain.AccessToken;
import com.harmony.kindless.oauth.service.AccessTokenService;
import com.harmony.kindless.shiro.OAuthAccessToken;
import com.harmony.kindless.util.SecurityUtils;

/**
 * @author wuxii@foxmail.com
 */
public class OAuthAuthenticationgFilter extends AccessControlFilter {

    private static final String X_ACCESS_TOKEN = "X-AccessToken";

    @Autowired
    private AccessTokenService accessTokenService;

    public OAuthAuthenticationgFilter(AccessTokenService accessTokenService) {
        this.accessTokenService = accessTokenService;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        return subject.isAuthenticated() || true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String accessToken = getAccessToken((HttpServletRequest) request);
        if (accessToken != null) {
            return loginWithAccessToken(accessToken, request, response);
        }
        throw new UnauthorizedException("unauthorized_request access_token not found");
    }

    protected boolean loginWithAccessToken(String token, ServletRequest request, ServletResponse response) {
        AccessToken accessToken = accessTokenService.findOne(token);
        String username = accessToken.getUsername();
        SecurityUtils.login(new OAuthAccessToken(username, token));
        return true;
    }

    protected String getAccessToken(HttpServletRequest request) {
        return request.getHeader(X_ACCESS_TOKEN);
    }

    @Override
    protected void cleanup(ServletRequest request, ServletResponse response, Exception existing) throws ServletException, IOException {
        // FIXME 异常处理
    }

    public AccessTokenService getAccessTokenService() {
        return accessTokenService;
    }

    public void setAccessTokenService(AccessTokenService accessTokenService) {
        this.accessTokenService = accessTokenService;
    }

}
