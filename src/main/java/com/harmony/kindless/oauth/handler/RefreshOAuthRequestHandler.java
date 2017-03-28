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
import com.harmony.kindless.oauth.repository.AccessTokenRepository;
import com.harmony.kindless.oauth.repository.ClientInfoRepository;
import com.harmony.kindless.oauth.service.AccessTokenService;
import com.harmony.kindless.oauth.validator.RefreshValidator;

/**
 * @author wuxii@foxmail.com
 */
public class RefreshOAuthRequestHandler extends AbstractOAuthRequestHandler {

    private List<OAuthRequestValidator> validators;
    private AccessTokenRepository accessTokenRepository;
    private AccessTokenService accessTokenService;
    private ClientInfoRepository clientInfoRepository;

    @Override
    public GrantType getSupportedGrantType() {
        return GrantType.REFRESH_TOKEN;
    }

    @Override
    protected OAuthResponse doHandler(OAuthRequest request) throws OAuthSystemException {
        String refreshToken = request.getParam(OAuth.OAUTH_REFRESH_TOKEN);
        ClientInfo clientInfo = clientInfoRepository.findOne(request.getClientId());
        AccessToken accessToken = accessTokenService.refreshToken(refreshToken, clientInfo);
        return OAuthASResponse//
                .tokenResponse(HttpServletResponse.SC_OK)//
                .setAccessToken(accessToken.getAccessToken())//
                .setExpiresIn(String.valueOf(accessToken.getExpiresIn()))//
                .setRefreshToken(accessToken.getRefreshToken())//
                .buildJSONMessage();
    }

    @Override
    public List<OAuthRequestValidator> getOAuthRequestValidators() {
        if (validators == null) {
            validators = new ArrayList<>();
            validators.add(new RefreshValidator(accessTokenRepository));
            validators = Collections.unmodifiableList(validators);
        }
        return validators;
    }

    public AccessTokenRepository getAccessTokenRepository() {
        return accessTokenRepository;
    }

    public void setAccessTokenRepository(AccessTokenRepository accessTokenRepository) {
        this.accessTokenRepository = accessTokenRepository;
    }

}
