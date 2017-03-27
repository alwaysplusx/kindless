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
import com.harmony.kindless.oauth.repository.ClientInfoRepository;
import com.harmony.kindless.oauth.service.AccessTokenService;
import com.harmony.kindless.oauth.validator.ClientInfoValidator;

/**
 * @author wuxii@foxmail.com
 */
public class ClientCredentialsOAuthRequestHandler extends AbstractOAuthRequestHandler {

    private List<OAuthRequestValidator> validators;
    private ClientInfoRepository clientInfoRepository;
    private AccessTokenService accessTokenService;

    @Override
    public GrantType getSupportedGrantType() {
        return GrantType.CLIENT_CREDENTIALS;
    }

    @Override
    protected void doHandler(OAuthRequest request, NativeWebRequest webRequest) throws OAuthSystemException, IOException {
        AccessToken accessToken = accessTokenService.createAccessToken(null);
        OAuthResponseWriter.bodyWriter(webRequest)
                .writeResponse(OAuthASResponse//
                        .tokenResponse(HttpServletResponse.SC_OK)//
                        .setAccessToken(accessToken.getAccessToken())//
                        .setExpiresIn(String.valueOf(accessToken.getExpiresIn()))//
                        .buildJSONMessage());
    }

    @Override
    public List<OAuthRequestValidator> getOAuthRequestValidators() {
        if (validators == null) {
            validators = new ArrayList<>();
            validators.add(new ClientInfoValidator(clientInfoRepository));
            validators = Collections.unmodifiableList(validators);
        }
        return validators;
    }

    public ClientInfoRepository getClientInfoRepository() {
        return clientInfoRepository;
    }

    public void setClientInfoRepository(ClientInfoRepository clientInfoRepository) {
        this.clientInfoRepository = clientInfoRepository;
    }

}
