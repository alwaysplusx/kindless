package com.harmony.kindless.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import com.harmony.kindless.util.SecurityUtils;
import com.harmony.umbrella.json.Json;
import com.harmony.umbrella.web.WebRender;
import com.harmony.umbrella.web.controller.Response;

/**
 * @author wuxii@foxmail.com
 */
public abstract class AbstractTokenFilter extends AccessControlFilter {

    protected abstract String getToken(HttpServletRequest request, HttpServletResponse response);

    protected abstract AuthenticationToken createToken(String token);

    protected boolean executeLogin(String token, HttpServletRequest request, HttpServletResponse response) {
        SecurityUtils.login(createToken(token));
        return true;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        Subject subject = getSubject(request, response);
        return subject.isAuthenticated();
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String token = getToken(req, resp);
        if (token != null) {
            return executeLogin(token, req, resp);
        }
        throw new IllegalStateException("token not found!");
    }

    @Override
    protected void cleanup(ServletRequest request, ServletResponse response, Exception existing)
            throws ServletException, IOException {
        Exception exception = existing;
        if (existing != null) {
            exception = null;
            try {
                HttpServletResponse resp = (HttpServletResponse) response;
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                WebRender//
                        .create(resp)//
                        .withContentType("application/json", "utf-8")//
                        .render(Json.toJson(Response.error(401001, existing.getMessage())));
            } catch (Exception e) {
                exception = e;
            }
        }
        super.cleanup(request, response, exception);
    }
}
