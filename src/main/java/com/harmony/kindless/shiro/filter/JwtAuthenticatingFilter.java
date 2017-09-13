package com.harmony.kindless.shiro.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;

import com.harmony.kindless.core.domain.User;
import com.harmony.kindless.core.service.UserService;

/**
 * @author wuxii@foxmail.com
 */
public class JwtAuthenticatingFilter extends AbstractTokenFilter {

    private static final String JWT_X_HEADER = "X-Authorization";

    @Autowired
    private UserService userService;

    public JwtAuthenticatingFilter() {
    }

    public JwtAuthenticatingFilter(UserService userService) {
        this.userService = userService;
    }

    @Override 
    protected String getToken(HttpServletRequest request, HttpServletResponse response) {
        return request.getHeader(JWT_X_HEADER);
    }

    @Override
    protected AuthenticationToken createToken(String token) {
        User user = userService.findUserByWebToken(token);
        return new UsernamePasswordToken(user.getUsername(), user.getPassword());
    }

}