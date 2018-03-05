package com.harmony.kindless.shiro.authc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import com.harmony.kindless.core.service.SecurityService;
import com.harmony.kindless.shiro.JwtToken;
import com.harmony.kindless.shiro.JwtTokenVerifier;
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
        JwtToken jwtToken = getRequestJwtToken(request);
        if (jwtToken == null) {
            throw new AuthenticationException("token not found");
        }
        if (jwtToken.isExpired()) {
            throw new AuthenticationException("token expired");
        }
        Subject subject = getSubject(request, response);
        return executeLogin(subject, jwtToken);
    }

    protected boolean executeLogin(Subject subject, JwtToken token) {
        securityService.login(token);
        return true;
    }

    protected JwtToken getRequestJwtToken(HttpServletRequest request) {
        return SecurityUtils.getRequestToken(request, tokenName);
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

    protected JwtTokenVerifier getTokenVerifier() {
        return securityService;
    }

    public SecurityService getSecurityService() {
        return securityService;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

}