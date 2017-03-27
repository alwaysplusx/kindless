package com.harmony.kindless.oauth.handler;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.web.context.request.NativeWebRequest;

import com.harmony.kindless.oauth.OAuthRequestHandler;
import com.harmony.kindless.oauth.OAuthRequestValidator;
import com.harmony.kindless.oauth.OAuthResponseWriter;

/**
 * @author wuxii@foxmail.com
 */
public abstract class AbstractOAuthRequestHandler implements OAuthRequestHandler {

    public abstract GrantType getSupportedGrantType();

    public abstract List<OAuthRequestValidator> getOAuthRequestValidators();

    protected abstract void doHandler(OAuthRequest request, NativeWebRequest webRequest) throws OAuthSystemException, IOException;

    @Override
    public boolean support(GrantType grantType) {
        if (grantType == null) {
            return false;
        }
        return grantType.equals(getSupportedGrantType());
    }

    @Override
    public void handle(OAuthRequest request, NativeWebRequest webRequest) throws OAuthSystemException, IOException {
        for (OAuthRequestValidator validator : getOAuthRequestValidators()) {
            try {
                validator.valid(request);
            } catch (OAuthProblemException e) {
                HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
                OAuthResponseWriter//
                        .bodyWriter(response)//
                        .writeResponse(OAuthResponse//
                                .errorResponse(HttpServletResponse.SC_BAD_REQUEST)//
                                .setError(e.getError())//
                                .setErrorDescription(e.getDescription())//
                                .buildJSONMessage());
                return;
            }
        }
        doHandler(request, webRequest);
    }

}
