package com.harmony.kindless.oauth.handler;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import com.harmony.kindless.oauth.OAuthDispatcher;
import com.harmony.kindless.oauth.OAuthRequestHandler;

/**
 * @author wuxii@foxmail.com
 */
public abstract class AbstractOAuthRequestHandler implements OAuthRequestHandler {

    public abstract GrantType getSupportedGrantType();

    @Override
    public boolean support(OAuthRequest request) {
        GrantType grantType = OAuthDispatcher.getGrantType(request);
        return grantType != null && grantType.equals(getSupportedGrantType());
    }

}
