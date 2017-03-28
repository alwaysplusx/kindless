package com.harmony.kindless.oauth.handler;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import com.harmony.kindless.oauth.OAuthRequestHandler;
import com.harmony.kindless.oauth.OAuthRequestValidator;

/**
 * @author wuxii@foxmail.com
 */
public abstract class AbstractOAuthRequestHandler implements OAuthRequestHandler {

    public abstract List<OAuthRequestValidator> getOAuthRequestValidators();

    protected abstract OAuthResponse doHandler(OAuthRequest request) throws OAuthSystemException;

    public abstract GrantType getSupportedGrantType();

    @Override
    public boolean support(GrantType grantType) {
        return grantType != null && grantType.equals(getSupportedGrantType());
    }

    @Override
    public OAuthResponse handle(OAuthRequest request) throws OAuthSystemException {
        for (OAuthRequestValidator validator : getOAuthRequestValidators()) {
            try {
                validator.valid(request);
            } catch (OAuthProblemException e) {
                return OAuthResponse//
                        .errorResponse(HttpServletResponse.SC_BAD_REQUEST)//
                        .setError(e.getError())//
                        .setErrorDescription(e.getDescription())//
                        .buildJSONMessage();
            }
        }
        return doHandler(request);
    }

}
