package com.harmony.kindless.oauth.controller;

import java.io.IOException;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

import com.harmony.kindless.oauth.OAuthDispatcher;
import com.harmony.kindless.oauth.OAuthUtils;
import com.harmony.kindless.oauth.OAuthUtils.OAuthResponseType;
import com.harmony.kindless.util.SecurityUtils;

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
     * <ul>
     * <li>authorization_code: 第一阶段获取scope_code(请求链接如:
     * {@code http://server/oauth/authorize?client_id=xxx&redirect_uri=xxx&response_type=code})
     * <li>implicit: 简单授权模式, 静默授权用户无感知
     * </ul>
     * 
     * @param webRequest
     *            request
     * @throws IOException
     * 
     * @see <a href='http://open.weibo.com/wiki/Oauth2/authorize'>http://open.weibo.com/wiki/Oauth2/authorize</a>
     */
    @RequestMapping("/authorize")
    public View authorize(NativeWebRequest webRequest) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            return new InternalResourceView("/authorize.html");
        }
        OAuthResponse oauthResponse = null;
        try {
            OAuthRequest oauthRequest = OAuthUtils.codeRequest(webRequest);
            oauthResponse = dispatcher.dispatch(oauthRequest);
        } catch (OAuthProblemException e) {
            oauthResponse = OAuthUtils.parseException(e, OAuthResponseType.Json);
        }
        return OAuthUtils.toResponseView(oauthResponse);
    }

    /**
     * 负责以下grant_type
     * <li>authorization_code: 第二阶段通过scope_code换取access_token
     * <li>password: 用户直接在第三方上通过用户名密码进行授权
     * <li>client_credentials: 授权服务器对第三方的直接授权
     * <li>refresh_token: 刷新access_token
     * 
     * @param webRequest
     *            request
     * @throws IOException
     */
    @RequestMapping("/token")
    public View token(NativeWebRequest webRequest) throws IOException {
        OAuthResponse oauthResponse = null;
        try {
            OAuthRequest oauthRequest = OAuthUtils.tokenRequest(webRequest);
            oauthResponse = dispatcher.dispatch(oauthRequest);
        } catch (OAuthProblemException e) {
            oauthResponse = OAuthUtils.parseException(e, OAuthResponseType.Json);
        }
        return OAuthUtils.toResponseView(oauthResponse);
    }

    public void setOAuthDispatcher(OAuthDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public OAuthDispatcher getOAuthDispatcher() {
        return dispatcher;
    }

}
