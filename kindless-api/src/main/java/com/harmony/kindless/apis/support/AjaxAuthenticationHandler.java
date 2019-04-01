package com.harmony.kindless.apis.support;

import com.harmony.kindless.apis.ResponseCodes;
import com.harmony.kindless.apis.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wuxii
 */
@Slf4j
public class AjaxAuthenticationHandler implements LogoutHandler, AccessDeniedHandler, AuthenticationEntryPoint {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        log.warn("request access denied", accessDeniedException);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(ResponseCodes.UNAUTHORIZED.toResponse().toJson());
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        log.warn("request authentication exception", authException);
        Exception lastException = (Exception) request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(ResponseUtils.fallback(lastException == null ? authException : lastException, ResponseCodes.UNAUTHORIZED).toJson());
    }

}
