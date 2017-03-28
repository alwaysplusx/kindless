package com.harmony.kindless.oauth;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;

/**
 * @author wuxii@foxmail.com
 */
public interface OAuthRequestHandler {

    boolean support(GrantType grantType);

    OAuthResponse handle(OAuthRequest request) throws OAuthSystemException;

}
