package com.harmony.kindless.oauth.handler;

import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import com.harmony.kindless.oauth.domain.AccessToken;
import com.harmony.kindless.oauth.domain.ClientInfo;
import com.harmony.kindless.oauth.repository.AccessTokenRepository;
import com.harmony.kindless.oauth.repository.ClientInfoRepository;
import com.harmony.kindless.oauth.service.AccessTokenService;

/**
 * @author wuxii@foxmail.com
 */
public class RefreshOAuthRequestHandler extends AbstractOAuthRequestHandler {

    private AccessTokenRepository accessTokenRepository;
    private AccessTokenService accessTokenService;
    private ClientInfoRepository clientInfoRepository;

    @Override
    public GrantType getSupportedGrantType() {
        return GrantType.REFRESH_TOKEN;
    }

    @Override
    public OAuthResponse handle(OAuthRequest request) throws OAuthProblemException {
        String refreshToken = request.getParam(OAuth.OAUTH_REFRESH_TOKEN);
        ClientInfo clientInfo = clientInfoRepository.findOne(request.getClientId());
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
    }

    public AccessTokenRepository getAccessTokenRepository() {
        return accessTokenRepository;
    }

    public void setAccessTokenRepository(AccessTokenRepository accessTokenRepository) {
        this.accessTokenRepository = accessTokenRepository;
    }

}
