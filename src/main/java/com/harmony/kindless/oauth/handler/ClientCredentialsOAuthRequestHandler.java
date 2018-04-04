package com.harmony.kindless.oauth.handler;

import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;

import com.harmony.kindless.core.domain.ClientInfo;
import com.harmony.kindless.oauth.domain.AccessToken;
import com.harmony.kindless.oauth.service.AccessTokenService;
import com.harmony.kindless.oauth.service.ClientInfoService;

/**
 * grant_type = 'client_credentials'
 * <p>
 * 对第三方授权, 无用户参与(或者说第三方就是用户)
 * 
 * @author wuxii@foxmail.com
 */
public class ClientCredentialsOAuthRequestHandler extends AbstractOAuthRequestHandler {

    @Autowired
    private ClientInfoService clientInfoService;
    @Autowired
    private AccessTokenService accessTokenService;

    @Override
    public GrantType getSupportedGrantType() {
        return GrantType.CLIENT_CREDENTIALS;
    }

    @Override
    public OAuthResponse handle(OAuthRequest request) throws OAuthProblemException, OAuthSystemException {
        String clientId = request.getClientId();
        String clientSecret = request.getClientSecret();
        String redirectURI = request.getRedirectURI();

        ClientInfo clientInfo = clientInfoService.findById(clientId);
        if (clientInfo == null || !clientInfo.getClientSecret().equals(clientSecret)) {
            throw OAuthProblemException.error("invalid client_id or client_secret");
        }

        if (!clientInfo.getRedirectUri().equals(redirectURI)) {
            throw OAuthProblemException.error("invalid redirect_uri");
        }

        AccessToken accessToken = accessTokenService.grant(clientInfo);

        return OAuthASResponse//
                .tokenResponse(HttpServletResponse.SC_OK)//
                .setAccessToken(accessToken.getAccessToken())//
                .setExpiresIn(String.valueOf(accessToken.getExpiresIn()))//
                .buildJSONMessage();
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
