package com.harmony.kindless.oauth.handler;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;

/**
 * @author wuxii@foxmail.com
 */
public class CodeOAuthRequestHandler {

    public boolean support(OAuthRequest request) {
        String responseType = request.getParam(OAuth.OAUTH_RESPONSE_TYPE);
        return OAuth.OAUTH_CODE.equals(responseType);
    }

    public OAuthResponse handle(OAuthRequest request) throws OAuthSystemException {
        return null;
    }

}
