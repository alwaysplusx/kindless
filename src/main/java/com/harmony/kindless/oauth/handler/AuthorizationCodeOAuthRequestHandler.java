package com.harmony.kindless.oauth.handler;

import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;

import com.harmony.kindless.oauth.domain.AccessToken;
import com.harmony.kindless.oauth.domain.ClientInfo;
import com.harmony.kindless.oauth.domain.ScopeCode;
import com.harmony.kindless.oauth.service.AccessTokenService;
import com.harmony.kindless.oauth.service.ScopeCodeService;

/**
 * grant_type = 'authorization_code'
 * <p>
 * 第三方通过一阶段中的scope code来换取access_token
 * 
 * @author wuxii@foxmail.com
 * @see ScopeCodeOAuthRequestHandler
 */
public class AuthorizationCodeOAuthRequestHandler extends AbstractOAuthRequestHandler {

    @Autowired
    private ScopeCodeService scopeCodeService;
    @Autowired
    private AccessTokenService accessTokenService;

    public AuthorizationCodeOAuthRequestHandler() {
    }

    public AuthorizationCodeOAuthRequestHandler(ScopeCodeService scopeCodeService, AccessTokenService accessTokenService) {
        this.scopeCodeService = scopeCodeService;
        this.accessTokenService = accessTokenService;
    }

    @Override
    public GrantType getSupportedGrantType() {
        return GrantType.AUTHORIZATION_CODE;
    }

    @Override
    public OAuthResponse handle(OAuthRequest request) throws OAuthProblemException, OAuthSystemException {
        String clientId = request.getClientId();
        String clientSecret = request.getClientSecret();
        String redirectURI = request.getRedirectURI();
        String code = request.getParam(OAuth.OAUTH_CODE);

        ScopeCode scopeCode = scopeCodeService.findById(code);
        if (scopeCode == null //
                || !scopeCode.getClientId().equals(clientId)) {
            throw OAuthProblemException.error("invalid code");
        }

        if (scopeCode.isExpired()) {
            throw OAuthProblemException.error("code expired");
        }

        ClientInfo clientInfo = scopeCode.getClientInfo();

        if (clientInfo == null || !clientInfo.getClientSecret().equals(clientSecret)) {
            throw OAuthProblemException.error("invalid client_id or client_secret");
        }

        if (clientInfo.isExpired()) {
            throw OAuthProblemException.error("client expired");
        }

        if (!clientInfo.getRedirectUri().equals(redirectURI)) {
            throw OAuthProblemException.error("invalid redirect_uri");
        }

        AccessToken accessToken = accessTokenService.grant(scopeCode);
        return OAuthASResponse//
                .tokenResponse(HttpServletResponse.SC_OK)//
                .setAccessToken(accessToken.getAccessToken())//
                .setExpiresIn(String.valueOf(accessToken.getExpiresIn()))//
                .setParam("uid", accessToken.getUsername())//
                .setRefreshToken(accessToken.getRefreshToken()).buildJSONMessage();
    }

    public ScopeCodeService getScopeCodeService() {
        return scopeCodeService;
    }

    public void setScopeCodeService(ScopeCodeService scopeCodeService) {
        this.scopeCodeService = scopeCodeService;
    }

    public AccessTokenService getAccessTokenService() {
        return accessTokenService;
    }

    public void setAccessTokenService(AccessTokenService accessTokenService) {
        this.accessTokenService = accessTokenService;
    }

}
