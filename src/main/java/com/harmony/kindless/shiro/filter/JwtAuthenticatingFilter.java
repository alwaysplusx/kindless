package com.harmony.kindless.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.harmony.kindless.core.domain.User;
import com.harmony.kindless.core.service.UserService;
import com.harmony.kindless.util.SecurityUtils;
import com.harmony.umbrella.json.Json;
import com.harmony.umbrella.web.WebRender;
import com.harmony.umbrella.web.controller.Response;

/**
 * @author wuxii@foxmail.com
 */
public class JwtAuthenticatingFilter extends AccessControlFilter {

    private static final String JWT_X_HEADER = "X-Authorization";

    @Autowired
    private UserService userService;

    public JwtAuthenticatingFilter() {
    }

    public JwtAuthenticatingFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        return subject.isAuthenticated() || true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String token = getJsonWebToken((HttpServletRequest) request);
        if (token != null) {
            return loginWithJsonWebToken(token, request, response);
        }
        throw new UnauthorizedException("unauthorized_request jwt not found");
    }

    private boolean loginWithJsonWebToken(String token, ServletRequest request, ServletResponse response) throws Exception {
        User user = userService.findUserByWebToken(token);
        if (user != null) {
            SecurityUtils.login(new UsernamePasswordToken(user.getUsername(), user.getPassword()));
            return true;
        }
        throw new UnauthorizedException("jwt token user not found");
    }

    @Override
    protected void cleanup(ServletRequest request, ServletResponse response, Exception existing) throws ServletException, IOException {
        if (existing != null) {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            // FIXME Response基本描述与详细描述的区分
            WebRender//
                    .create(resp)//
                    .withContentType("application/json", "utf-8")//
                    .render(Json.toJson(Response.error(401001, existing.getMessage())));
        }
        super.cleanup(request, response, null);
    }

    protected String getJsonWebToken(HttpServletRequest request) {
        return request.getHeader(JWT_X_HEADER);
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}