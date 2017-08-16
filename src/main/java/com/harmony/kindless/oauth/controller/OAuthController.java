package com.harmony.kindless.oauth.controller;

import java.io.IOException;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import com.harmony.kindless.oauth.OAuthDispatcher;
import com.harmony.kindless.oauth.OAuthUtils;
import com.harmony.kindless.oauth.OAuthUtils.OAuthResponseType;

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

    @Autowired
    private OAuthDispatcher dispatcher;

    /**
     * 负责以下grant_type
     * <li>authorization_code 第一阶段获取code(请求链接如:
     * {@code http://server/oauth/authorize?client_id=xxx&redirect_uri=xxx&response_type=code})
     * <li>implicit
     * 
     * @throws IOException
     * 
     * @see {@link http://open.weibo.com/wiki/Oauth2/authorize}
     */
    @RequestMapping("/authorize")
    public void authorize(NativeWebRequest webRequest) throws IOException {
        OAuthResponse oauthResponse = null;
        try {
            OAuthRequest oauthRequest = OAuthUtils.codeRequest(webRequest);
            oauthResponse = dispatcher.dispatch(oauthRequest);
        } catch (OAuthProblemException e) {
            oauthResponse = OAuthUtils.parseException(e, OAuthResponseType.Json);
        }
        OAuthUtils//
                .createWriter(webRequest)//
                .writeResponse(oauthResponse);
    }

    /**
     * 负责以下grant_type
     * <li>authorization_code 第二阶段通过code换取token
     * <li>password
     * <li>client_credentials
     * <li>refresh_token
     * 
     * @throws IOException
     * @throws OAuthProblemException
     * @throws OAuthSystemException
     */
    @RequestMapping(path = "/token")
    public void token(NativeWebRequest webRequest) throws IOException {
        OAuthResponse oauthResponse = null;
        try {
            OAuthRequest oauthRequest = OAuthUtils.tokenRequest(webRequest);
            oauthResponse = dispatcher.dispatch(oauthRequest);
        } catch (OAuthProblemException e) {
            oauthResponse = OAuthUtils.parseException(e, OAuthResponseType.Json);
        }
        OAuthUtils//
                .createWriter(webRequest)//
                .writeResponse(oauthResponse);
    }

}
