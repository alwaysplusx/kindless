package com.harmony.kindless.oauth.handler;

import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import com.harmony.kindless.oauth.domain.AccessToken;
import com.harmony.kindless.oauth.domain.ClientInfo;
import com.harmony.kindless.oauth.repository.ClientInfoRepository;
import com.harmony.kindless.oauth.service.AccessTokenService;

/**
 * @author wuxii@foxmail.com
 */
public class ClientCredentialsOAuthRequestHandler extends AbstractOAuthRequestHandler {

    private ClientInfoRepository clientInfoRepository;
    private AccessTokenService accessTokenService;

    @Override
    public GrantType getSupportedGrantType() {
        return GrantType.CLIENT_CREDENTIALS;
    }

    @Override
    public OAuthResponse handle(OAuthRequest request) throws OAuthProblemException, OAuthSystemException {
        ClientInfo clientInfo = clientInfoRepository.findOne(request.getClientId());
        AccessToken accessToken = accessTokenService.createAccessToken(clientInfo);
        // client info valid
        return OAuthASResponse//
                .tokenResponse(HttpServletResponse.SC_OK)//
                .setAccessToken(accessToken.getAccessToken())//
                .setExpiresIn(String.valueOf(accessToken.getExpiresIn()))//
                .buildJSONMessage();
    }

    public ClientInfoRepository getClientInfoRepository() {
        return clientInfoRepository;
    }

    public void setClientInfoRepository(ClientInfoRepository clientInfoRepository) {
        this.clientInfoRepository = clientInfoRepository;
    }

}
