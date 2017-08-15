package com.harmony.kindless.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

/**
 * @author wuxii@foxmail.com
 */
public class OAuthAuthenticationgFilter extends AccessControlFilter {

    private static final String X_ACCESS_TOKEN = "X-AccessToken";

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        return subject.isAuthenticated();
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String accessToken = getAccessToken((HttpServletRequest) request);
        if (accessToken != null) {
            return loginWithAccessToken(accessToken, request, response);
        }
        throw new UnauthorizedException("unauthorized_request access_token not found");
    }

    protected boolean loginWithAccessToken(String accessToken, ServletRequest request, ServletResponse response) {
        return false;
    }

    protected String getAccessToken(HttpServletRequest request) {
        return request.getHeader(X_ACCESS_TOKEN);
    }

    @Override
    protected void cleanup(ServletRequest request, ServletResponse response, Exception existing) throws ServletException, IOException {
        super.cleanup(request, response, existing);
    }

}
