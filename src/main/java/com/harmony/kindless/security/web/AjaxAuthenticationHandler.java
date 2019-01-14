package com.harmony.kindless.security.web;

import com.harmony.kindless.apis.ResponseCodes;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wuxii
 */
public class AjaxAuthenticationHandler implements LogoutHandler, AccessDeniedHandler, AuthenticationEntryPoint {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        response.getWriter().write(ResponseCodes.UNAUTHORIZED.toResponse().toJson());
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        response.getWriter().write(ResponseCodes.UNAUTHORIZED.toResponse().toJson());
    }

}
