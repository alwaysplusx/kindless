package com.harmony.kindless.security.support;

import com.auth0.jwt.JWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author wuxii
 */
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        // TODO 授权成功, 生成jwt
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String tokenValue = JWT.create()
                .withJWTId("")
                .withIssuedAt(new Date())
                .withIssuer("kindless")
                .withExpiresAt(new Date())
                .withNotBefore(new Date())
                .withSubject("")
                .sign(null);
        response.getWriter().write(tokenValue);
        response.flushBuffer();
    }

}
