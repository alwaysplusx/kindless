package com.harmony.kindless.security.support;

import com.harmony.kindless.user.service.UserTokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wuxii
 */
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private UserTokenService userTokenService;

    public AjaxAuthenticationSuccessHandler(UserTokenService userTokenService) {
        this.userTokenService = userTokenService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        // TODO 授权成功, 生成jwt
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        // refresh to response
        response.getWriter().write("");
        response.flushBuffer();
    }

    public UserTokenService getUserTokenService() {
        return userTokenService;
    }

    public void setUserTokenService(UserTokenService userTokenService) {
        this.userTokenService = userTokenService;
    }

}
