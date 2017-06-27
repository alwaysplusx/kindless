package com.harmony.kindless.oauth.handler;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import com.harmony.kindless.domain.domain.User;
import com.harmony.kindless.domain.service.UserService;
import com.harmony.kindless.oauth.OAuthRequestHandler;
import com.harmony.kindless.oauth.domain.AccessToken;
import com.harmony.kindless.oauth.domain.ClientInfo;
import com.harmony.kindless.oauth.service.AccessTokenService;
import com.harmony.kindless.oauth.service.ClientInfoService;

/**
 * @author wuxii@foxmail.com
 */
public class TokenOAuthRequestHandler implements OAuthRequestHandler {

    private ClientInfoService clientInfoService;

    private AccessTokenService accessTokenService;

    private UserService userService;

    @Override
    public boolean support(OAuthRequest request) {
        String responseType = request.getParam(OAuth.OAUTH_RESPONSE_TYPE);
        return OAuth.OAUTH_TOKEN.equals(responseType);
    }

    @Override
    public OAuthResponse handle(OAuthRequest request) throws OAuthProblemException, OAuthSystemException {
        String clientId = request.getClientId();
        String redirectURI = request.getRedirectURI();
        // FIXME current username
        String username = "wuxii";

        ClientInfo clientInfo = clientInfoService.findOne(clientId);
        if (clientInfo == null) {
            throw OAuthProblemException.error("invalid client_id");
        }
        if (!clientInfo.getRedirectUri().equals(redirectURI)) {
            throw OAuthProblemException.error("invalid redirect_uri");
        }

        User user = userService.findOne(username);

        AccessToken accessToken = accessTokenService.grant(user, clientInfo, GrantType.IMPLICIT);

        return OAuthASResponse//
                .tokenResponse(302)//
                .setAccessToken(accessToken.getAccessToken())//
                .setExpiresIn(String.valueOf(accessToken.getExpiresIn()))//
                .setRefreshToken(accessToken.getRefreshToken())//
                .buildQueryMessage();
    }

}
