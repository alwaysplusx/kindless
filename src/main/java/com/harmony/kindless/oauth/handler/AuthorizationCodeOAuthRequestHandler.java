package com.harmony.kindless.oauth.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.web.context.request.NativeWebRequest;

import com.harmony.kindless.oauth.OAuthRequestValidator;
import com.harmony.kindless.oauth.OAuthResponseWriter;
import com.harmony.kindless.oauth.domain.AccessToken;
import com.harmony.kindless.oauth.repository.AuthorizeCodeRepository;
import com.harmony.kindless.oauth.repository.ClientInfoRepository;
import com.harmony.kindless.oauth.service.AccessTokenService;
import com.harmony.kindless.oauth.validator.AuthorizeCodeValidator;
import com.harmony.kindless.oauth.validator.ClientInfoValidator;

/**
 * @author wuxii@foxmail.com
 */
public class AuthorizationCodeOAuthRequestHandler extends AbstractOAuthRequestHandler {

    private ClientInfoRepository clientInfoRepository;
    private AuthorizeCodeRepository authorizeCodeRepository;
    private List<OAuthRequestValidator> validators;
    private AccessTokenService accessTokenService;

    @Override
    public GrantType getSupportedGrantType() {
        return GrantType.AUTHORIZATION_CODE;
    }

    @Override
    protected void doHandler(OAuthRequest request, NativeWebRequest webRequest) throws OAuthSystemException, IOException {
        AccessToken accessToken = accessTokenService.createAccessToken("", null);
        OAuthResponseWriter//
                .redirectWriter(webRequest)//
                .writeResponse(OAuthASResponse//
                        .tokenResponse(HttpServletResponse.SC_OK)//
                        .setAccessToken(accessToken.getAccessToken())//
                        .setExpiresIn(String.valueOf(accessToken.getExpiresIn()))//
                        .setParam("uid", accessToken.getUsername())//
                        .setRefreshToken(accessToken.getRefreshToken())//
                        .buildQueryMessage());
    }

    @Override
    public List<OAuthRequestValidator> getOAuthRequestValidators() {
        if (validators == null) {
            validators = new ArrayList<>();
            validators.add(new ClientInfoValidator(clientInfoRepository));
            validators.add(new AuthorizeCodeValidator(authorizeCodeRepository));
            validators = Collections.unmodifiableList(validators);
        }
        return validators;
    }

    public void setOAuthRequestValidators(List<OAuthRequestValidator> validators) {
        this.validators = validators;
    }

    public ClientInfoRepository getClientInfoRepository() {
        return clientInfoRepository;
    }

    public void setClientInfoRepository(ClientInfoRepository clientInfoRepository) {
        this.clientInfoRepository = clientInfoRepository;
    }

    public AuthorizeCodeRepository getAuthorizeCodeRepository() {
        return authorizeCodeRepository;
    }

    public void setAuthorizeCodeRepository(AuthorizeCodeRepository authorizeCodeRepository) {
        this.authorizeCodeRepository = authorizeCodeRepository;
    }

}
