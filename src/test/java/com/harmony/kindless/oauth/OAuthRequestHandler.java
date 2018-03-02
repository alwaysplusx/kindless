package com.harmony.kindless.oauth;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;

/**
 * @author wuxii@foxmail.com
 */
public interface OAuthRequestHandler {

    boolean support(OAuthRequest request);

    OAuthResponse handle(OAuthRequest request) throws OAuthProblemException, OAuthSystemException;

}
