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
import com.harmony.kindless.oauth.domain.ScopeCode;
import com.harmony.kindless.oauth.repository.ClientInfoRepository;
import com.harmony.kindless.oauth.repository.ScopeCodeRepository;
import com.harmony.kindless.oauth.service.AccessTokenService;

/**
 * grant_type = 'authorization_code'
 * 
 * @author wuxii@foxmail.com
 */
public class AuthorizationCodeOAuthRequestHandler extends AbstractOAuthRequestHandler {

    private ClientInfoRepository clientInfoRepository;
    private ScopeCodeRepository scopeCodeRepository;
    private AccessTokenService accessTokenService;

    @Override
    public GrantType getSupportedGrantType() {
        return GrantType.AUTHORIZATION_CODE;
    }

    @Override
    public OAuthResponse handle(OAuthRequest request) throws OAuthProblemException, OAuthSystemException {
        String clientId = request.getClientId();
        ClientInfo clientInfo = clientInfoRepository.findOne(clientId);
        if (clientInfo == null) {
            // client info not found, throw exception
        }
        String code = request.getParam(OAuth.OAUTH_CODE);
        ScopeCode scopeCode = scopeCodeRepository.findByCodeAndClientId(code, clientId);
        if (scopeCode == null) {
            // scope code not found, throw exception
        }

        AccessToken accessToken = accessTokenService.createAccessToken(request.getParam(OAuth.OAUTH_CODE), clientInfo);
        return OAuthASResponse//
                .tokenResponse(HttpServletResponse.SC_OK)//
                .setAccessToken(accessToken.getAccessToken())//
                .setExpiresIn(String.valueOf(accessToken.getExpiresIn()))//
                .setParam("uid", accessToken.getUsername())//
                .setRefreshToken(accessToken.getRefreshToken()).buildJSONMessage();
    }

    public ScopeCodeRepository getScopeCodeRepository() {
        return scopeCodeRepository;
    }

    public void setScopeCodeRepository(ScopeCodeRepository scopeCodeRepository) {
        this.scopeCodeRepository = scopeCodeRepository;
    }

    public ClientInfoRepository getClientInfoRepository() {
        return clientInfoRepository;
    }

    public void setClientInfoRepository(ClientInfoRepository clientInfoRepository) {
        this.clientInfoRepository = clientInfoRepository;
    }

    public AccessTokenService getAccessTokenService() {
        return accessTokenService;
    }

    public void setAccessTokenService(AccessTokenService accessTokenService) {
        this.accessTokenService = accessTokenService;
    }

}
