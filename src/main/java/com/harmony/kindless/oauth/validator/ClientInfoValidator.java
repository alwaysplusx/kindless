package com.harmony.kindless.oauth.validator;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;

import com.harmony.kindless.oauth.OAuthRequestValidator;
import com.harmony.kindless.oauth.domain.ClientInfo;
import com.harmony.kindless.oauth.repository.ClientInfoRepository;

/**
 * @author wuxii@foxmail.com
 */
public class ClientInfoValidator implements OAuthRequestValidator {

    private ClientInfoRepository clientInfoRepository;

    public ClientInfoValidator(ClientInfoRepository clientInfoRepository) {
        this.clientInfoRepository = clientInfoRepository;
    }

    @Override
    public void valid(OAuthRequest request) throws OAuthProblemException {
        String clientId = request.getClientId();
        String clientSecret = request.getClientSecret();
        String redirectURI = request.getRedirectURI();
        ClientInfo clientInfo = clientInfoRepository.findOne(clientId);
        if (clientInfo == null //
                || !clientSecret.equals(clientInfo.getClientSecret()) //
                || !redirectURI.equals(clientInfo.getRedirectUri())) {
            throw OAuthProblemException.error(OAuthError.TokenResponse.INVALID_REQUEST, "invalid client info");
        }
    }

    public ClientInfoRepository getClientInfoRepository() {
        return clientInfoRepository;
    }

    public void setClientInfoRepository(ClientInfoRepository clientInfoRepository) {
        this.clientInfoRepository = clientInfoRepository;
    }

}
