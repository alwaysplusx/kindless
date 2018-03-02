package com.harmony.kindless.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import com.harmony.kindless.core.service.SecurityService;
import com.harmony.kindless.shiro.JwtToken;
import com.harmony.kindless.shiro.TokenVerifier;
import com.harmony.kindless.util.SecurityUtils;
import com.harmony.umbrella.web.Response;
import com.harmony.umbrella.web.WebRender;

/**
 * @author wuxii@foxmail.com
 */
public class JwtAuthenticatingFilter extends AccessControlFilter {

    public static final String DEFAULT_TOKEN_HEADER = "X-Authorization";

    private final String tokenName;
    private SecurityService securityService;

    public JwtAuthenticatingFilter() {
        this(DEFAULT_TOKEN_HEADER, null);
    }

    public JwtAuthenticatingFilter(String tokenName) {
        this.tokenName = tokenName;
    }

    public JwtAuthenticatingFilter(SecurityService securityService) {
        this(DEFAULT_TOKEN_HEADER, securityService);
    }

    public JwtAuthenticatingFilter(String tokenName, SecurityService securityService) {
        this.tokenName = tokenName;
        this.securityService = securityService;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        return subject.isAuthenticated();
    }

    @Override
    protected boolean onAccessDenied(ServletRequest req, ServletResponse resp) throws Exception {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        JwtToken token = getRequestJwtToken(request);
        if (token != null) {
            Subject subject = getSubject(request, response);
            return executeLogin(subject, token);
        }
        throw new AuthenticationException("token not found");
    }

    protected boolean executeLogin(Subject subject, JwtToken token) {
        securityService.login(token);
        return true;
    }

    protected JwtToken getRequestJwtToken(HttpServletRequest request) {
        JwtToken jwtToken = SecurityUtils.getRequestToken(request, tokenName);
        if (jwtToken != null && !getTokenVerifier().verify(jwtToken)) {
            throw new AuthorizationException("invalid token");
        }
        return jwtToken;
    }

    @Override
    protected void cleanup(ServletRequest request, ServletResponse response, Exception existing) throws ServletException, IOException {
        Exception exception = existing;
        if (existing != null) {
            exception = null;
            try {
                HttpServletResponse resp = (HttpServletResponse) response;
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                WebRender//
                        .create(resp)//
                        .withContentType("application/json", "utf-8")//
                        .render(Response.error(401001, existing.getMessage()).toJson());
            } catch (Exception e) {
                exception = e;
            }
        }
        super.cleanup(request, response, exception);
    }

    public String getTokenName() {
        return tokenName;
    }

    protected TokenVerifier getTokenVerifier() {
        return securityService;
    }

    public SecurityService getSecurityService() {
        return securityService;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

}