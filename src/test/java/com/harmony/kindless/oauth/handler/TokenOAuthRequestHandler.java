package com.harmony.kindless.oauth.handler;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;

import com.harmony.kindless.oauth.OAuthRequestHandler;
import com.harmony.kindless.oauth.domain.ClientInfo;
import com.harmony.kindless.oauth.service.ClientInfoService;

/**
 * 直接授权
 * <p>
 * 用户在第三方的引导下进行直接授权
 * 
 * @author wuxii@foxmail.com
 */
public class TokenOAuthRequestHandler implements OAuthRequestHandler {

    @Autowired
    private ClientInfoService clientInfoService;

    @Override
    public boolean support(OAuthRequest request) {
        // 根据responseType来判断是否支持直接授权
        String responseType = request.getParam(OAuth.OAUTH_RESPONSE_TYPE);
        return OAuth.OAUTH_TOKEN.equals(responseType);
    }

    @Override
    public OAuthResponse handle(OAuthRequest request) throws OAuthProblemException, OAuthSystemException {
        String clientId = request.getClientId();
        String redirectURI = request.getRedirectURI();
        // String username = null;// SecurityUtils.getUsername();

        ClientInfo clientInfo = clientInfoService.findById(clientId);
        if (clientInfo == null) {
            throw OAuthProblemException.error("invalid client_id");
        }
        if (!clientInfo.getRedirectUri().equals(redirectURI)) {
            throw OAuthProblemException.error("invalid redirect_uri");
        }
        return null;
        // User user = userService.findByUsername(username);
        // AccessToken accessToken = accessTokenService.grant(user, clientInfo, GrantType.IMPLICIT);
        // return OAuthASResponse//
        // .tokenResponse(302)//
        // .setAccessToken(accessToken.getAccessToken())//
        // .setExpiresIn(String.valueOf(accessToken.getExpiresIn()))//
        // .setRefreshToken(accessToken.getRefreshToken())//
        // .buildQueryMessage();
    }

}
