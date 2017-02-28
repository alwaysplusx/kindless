package com.harmony.kindless.oauth.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public void authorize(//
            @RequestParam("client_id") String clientId, //
            @RequestParam("redirect_uri") String redirectUri, //
            HttpServletRequest request) {

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

}
