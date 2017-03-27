package com.harmony.kindless.oauth.validator;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;

import com.harmony.kindless.oauth.OAuthRequestValidator;
import com.harmony.kindless.oauth.domain.AuthorizeCode;
import com.harmony.kindless.oauth.repository.AuthorizeCodeRepository;
import com.harmony.umbrella.util.StringUtils;

/**
 * @author wuxii@foxmail.com
 */
public class AuthorizeCodeValidator implements OAuthRequestValidator {

    private AuthorizeCodeRepository authorizeCodeRepository;

    public AuthorizeCodeValidator(AuthorizeCodeRepository authorizeCodeRepository) {
        this.authorizeCodeRepository = authorizeCodeRepository;
    }

    @Override
    public void valid(OAuthRequest request) throws OAuthProblemException {
        String clientId = request.getClientId();
        String code = request.getParam(OAuth.OAUTH_CODE);
        AuthorizeCode authorizeCode = authorizeCodeRepository.findByCodeAndClientId(code, clientId);
        if (StringUtils.isBlank(code) //
                || authorizeCode == null //
                || !clientId.equals(authorizeCode.getClientId())) {
            throw OAuthProblemException.error(OAuthError.CodeResponse.INVALID_REQUEST, "invalid authorization code");
        }
    }

    public AuthorizeCodeRepository getAuthorizeCodeRepository() {
        return authorizeCodeRepository;
    }

    public void setAuthorizeCodeRepository(AuthorizeCodeRepository authorizeCodeRepository) {
        this.authorizeCodeRepository = authorizeCodeRepository;
    }

}
