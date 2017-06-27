package com.harmony.kindless.oauth.handler;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import com.harmony.kindless.oauth.service.AccessTokenService;
import com.harmony.kindless.oauth.service.ClientInfoService;

/**
 * @author wuxii@foxmail.com
 */
public class RefreshOAuthRequestHandler extends AbstractOAuthRequestHandler {

    private ClientInfoService clientInfoService;
    private AccessTokenService accessTokenService;

    @Override
    public GrantType getSupportedGrantType() {
        return GrantType.REFRESH_TOKEN;
    }

    @Override
    public OAuthResponse handle(OAuthRequest request) throws OAuthProblemException {
        return null;
        /*
        String refreshToken = request.getParam(OAuth.OAUTH_REFRESH_TOKEN);
        ClientInfo clientInfo = clientInfoService.findOne(request.getClientId());
        AccessToken accessToken = accessTokenService.refreshToken(refreshToken, clientInfo);
        // RefreshValidator
        try {
        return OAuthASResponse//
        .tokenResponse(HttpServletResponse.SC_OK)//
        .setAccessToken(accessToken.getAccessToken())//
        .setExpiresIn(String.valueOf(accessToken.getExpiresIn()))//
        .setRefreshToken(accessToken.getRefreshToken())//
        .buildJSONMessage();
        } catch (OAuthSystemException e) {
        return null;
        }
        */
    }

    public ClientInfoService getClientInfoService() {
        return clientInfoService;
    }

    public void setClientInfoService(ClientInfoService clientInfoService) {
        this.clientInfoService = clientInfoService;
    }

    public AccessTokenService getAccessTokenService() {
        return accessTokenService;
    }

    public void setAccessTokenService(AccessTokenService accessTokenService) {
        this.accessTokenService = accessTokenService;
    }

}
