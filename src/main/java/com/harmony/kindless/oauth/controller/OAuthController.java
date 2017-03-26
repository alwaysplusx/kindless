package com.harmony.kindless.oauth.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * oauth2.0的分为4种模式, 外加一个access_token刷新:
 * <ul>
 * <li>授权码模式 (authorization code)
 * <li>简单模式 (implicit)
 * <li>密码模式 (resource owner password credentials)
 * <li>客户端模式 (client credentials)
 * <li>授权码刷新 (refresh token)
 * </ul>
 * 
 * @author wuxii@foxmail.com
 */
@RestController
@RequestMapping("/oauth")
public class OAuthController {

    /**
     * 负责以下grant_type
     * <li>authorization_code 第一阶段
     * <li>token
     */
    @GetMapping("/authorize")
    @ResponseBody
    public void authorize(//
            @RequestParam("client_id") String clientId, //
            @RequestParam("redirect_uri") String redirectUri, //
            @RequestParam("response_type") String responseType, //
            HttpServletRequest request, //
            HttpServletResponse response) {
        try {
            if ("code".equals(responseType)) {
                OAuthResponse oauthResponse = OAuthASResponse//
                        .authorizationResponse(request, HttpServletResponse.SC_OK)//
                        .location(redirectUri)//
                        .setCode("SplxlOBeZQQYbYS6WxSbIA")//
                        .buildQueryMessage();
                writeOAuthQueryResponse(response, oauthResponse);
            }
        } catch (OAuthSystemException e) {
        }
    }

    /**
     * 负责以下grant_type
     * <li>authorization_code 第二阶段
     * <li>password
     * <li>client_credentials
     * <li>refresh_token
     * 
     * @throws OAuthProblemException
     * @throws OAuthSystemException
     */
    @RequestMapping("/token")
    public void token(HttpServletRequest request) throws OAuthSystemException, OAuthProblemException {

    }

    public static void writeOAuthQueryResponse(HttpServletResponse response, OAuthResponse oAuthResponse) {
        final String locationUri = oAuthResponse.getLocationUri();
        try {

            final Map<String, String> headers = oAuthResponse.getHeaders();
            for (String key : headers.keySet()) {
                response.addHeader(key, headers.get(key));
            }

            response.setStatus(oAuthResponse.getResponseStatus());
            response.sendRedirect(locationUri);

        } catch (IOException e) {
            throw new IllegalStateException("Write OAuthResponse error", e);
        }
    }
}
