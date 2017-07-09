package com.harmony.kindless.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.harmony.kindless.domain.domain.User;
import com.harmony.kindless.domain.service.UserService;

/**
 * @author wuxii@foxmail.com
 */
public class JwtAuthenticatingFilter extends AuthenticatingFilter {

    private static final String JWT_X_HEADER = "X-Authorization";

    @Autowired
    private UserService userService;

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        String jwt = getJsonWebToken((HttpServletRequest) request);
        User user = userService.findUserByWebToken(jwt);
        return new UsernamePasswordToken(user.getUsername(), user.getPassword());
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return true;
    }

    protected String getJsonWebToken(HttpServletRequest request) {
        return request.getHeader(JWT_X_HEADER);
    }

}
