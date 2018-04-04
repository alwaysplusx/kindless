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

import com.harmony.kindless.jwt.JwtToken;
import com.harmony.kindless.jwt.JwtTokenFinder;
import com.harmony.umbrella.web.Response;
import com.harmony.umbrella.web.WebRender;

/**
 * @author wuxii@foxmail.com
 */
public class JwtAuthenticatingFilter extends AccessControlFilter {

    private JwtTokenFinder jwtTokenFinder;

    public JwtAuthenticatingFilter() {
    }

    public JwtAuthenticatingFilter(JwtTokenFinder jwtTokenFinder) {
        this.jwtTokenFinder = jwtTokenFinder;
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
        subject.login(new JwtAuthenticationToken(token));
        return true;
    }

    protected JwtToken getRequestJwtToken(HttpServletRequest request) {
        return jwtTokenFinder.find(request);
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

    public JwtTokenFinder getJwtTokenFinder() {
        return jwtTokenFinder;
    }

    public void setJwtTokenFinder(JwtTokenFinder jwtTokenFinder) {
        this.jwtTokenFinder = jwtTokenFinder;
    }
}