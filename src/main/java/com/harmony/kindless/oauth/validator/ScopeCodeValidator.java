package com.harmony.kindless.oauth.validator;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;

import com.harmony.kindless.oauth.OAuthRequestValidator;
import com.harmony.kindless.oauth.domain.ScopeCode;
import com.harmony.kindless.oauth.repository.ScopeCodeRepository;
import com.harmony.umbrella.util.StringUtils;

/**
 * @author wuxii@foxmail.com
 */
public class ScopeCodeValidator implements OAuthRequestValidator {

    private ScopeCodeRepository scopeCodeRespository;

    public ScopeCodeValidator(ScopeCodeRepository scopeCodeRespository) {
        this.scopeCodeRespository = scopeCodeRespository;
    }

    @Override
    public void valid(OAuthRequest request) throws OAuthProblemException {
        String clientId = request.getClientId();
        String code = request.getParam(OAuth.OAUTH_CODE);
        ScopeCode authorizeCode = scopeCodeRespository.findByCodeAndClientId(code, clientId);
        if (StringUtils.isBlank(code) //
                || authorizeCode == null //
                || !clientId.equals(authorizeCode.getClientId())) {
            throw OAuthProblemException.error(OAuthError.CodeResponse.INVALID_REQUEST, "invalid authorization code");
        }
    }

    public ScopeCodeRepository getScopeCodeRespository() {
        return scopeCodeRespository;
    }

    public void setScopeCodeRespository(ScopeCodeRepository scopeCodeRespository) {
        this.scopeCodeRespository = scopeCodeRespository;
    }

}
