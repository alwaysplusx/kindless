package com.harmony.kindless.security.support;

import com.harmony.kindless.apis.ResponseCodes;
import com.harmony.kindless.security.IdentityUserDetails;
import com.harmony.kindless.security.jwt.JwtTokenGenerator;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wuxii
 */
public class AjaxAuthenticationHandler implements AuthenticationSuccessHandler, LogoutHandler, AccessDeniedHandler, AuthenticationEntryPoint {

    private static final String RESPONSE_TEXT_PATTERN = "{\"%s\":\"%s\"}";
    private static final String DEFAULT_RESPONSE_NAME = "token";

    private JwtTokenGenerator jwtTokenGenerator;
    private final String responseName;

    public AjaxAuthenticationHandler() {
        this(DEFAULT_RESPONSE_NAME);
    }

    public AjaxAuthenticationHandler(String responseName) {
        this(responseName, new Auth0JwtTokenHandler());
    }

    public AjaxAuthenticationHandler(JwtTokenGenerator jwtTokenGenerator) {
        this(DEFAULT_RESPONSE_NAME, jwtTokenGenerator);
    }

    public AjaxAuthenticationHandler(String responseName, JwtTokenGenerator jwtTokenGenerator) {
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.responseName = responseName;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof IdentityUserDetails)) {
            // TODO authentication不支持的类型
            throw new RuntimeException("");
        }
        String tokenValue = jwtTokenGenerator.generate((IdentityUserDetails) principal, request);
        String responseText = buildResponseText(tokenValue);
        response.getWriter().write(responseText);
        response.flushBuffer();
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // TODO 清空jwt的缓存逻辑
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

    protected String buildResponseText(String tokenValue) {
        return StringUtils.hasText(responseName)
                ? String.format(RESPONSE_TEXT_PATTERN, responseName, tokenValue)
                : tokenValue;
    }

    public JwtTokenGenerator getJwtTokenGenerator() {
        return jwtTokenGenerator;
    }

    public void setJwtTokenGenerator(JwtTokenGenerator jwtTokenGenerator) {
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

}
