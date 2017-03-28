package com.harmony.kindless.oauth.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import com.harmony.kindless.oauth.OAuthRequestValidator;
import com.harmony.kindless.oauth.domain.AccessToken;
import com.harmony.kindless.oauth.domain.ClientInfo;
import com.harmony.kindless.oauth.repository.ClientInfoRepository;
import com.harmony.kindless.oauth.repository.ScopeCodeRepository;
import com.harmony.kindless.oauth.service.AccessTokenService;
import com.harmony.kindless.oauth.validator.ClientInfoValidator;
import com.harmony.kindless.oauth.validator.ScopeCodeValidator;

/**
 * @author wuxii@foxmail.com
 */
public class AuthorizationCodeOAuthRequestHandler extends AbstractOAuthRequestHandler {

    private ClientInfoRepository clientInfoRepository;
    private ScopeCodeRepository scopeCodeRepository;
    private List<OAuthRequestValidator> validators;
    private AccessTokenService accessTokenService;

    @Override
    public GrantType getSupportedGrantType() {
        return GrantType.AUTHORIZATION_CODE;
    }

    @Override
    protected OAuthResponse doHandler(OAuthRequest request) throws OAuthSystemException {
        ClientInfo clientInfo = clientInfoRepository.findOne(request.getClientId());
        AccessToken accessToken = accessTokenService.createAccessToken(request.getParam(OAuth.OAUTH_CODE), clientInfo);
        return OAuthASResponse//
                .tokenResponse(HttpServletResponse.SC_OK)//
                .setAccessToken(accessToken.getAccessToken())//
                .setExpiresIn(String.valueOf(accessToken.getExpiresIn()))//
                .setParam("uid", accessToken.getUsername())//
                .setRefreshToken(accessToken.getRefreshToken()).buildJSONMessage();
    }

    @Override
    public List<OAuthRequestValidator> getOAuthRequestValidators() {
        if (validators == null) {
            validators = new ArrayList<>();
            validators.add(new ClientInfoValidator(clientInfoRepository));
            validators.add(new ScopeCodeValidator(scopeCodeRepository));
            validators = Collections.unmodifiableList(validators);
        }
        return validators;
    }

    public void setOAuthRequestValidators(List<OAuthRequestValidator> validators) {
        this.validators = validators;
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
