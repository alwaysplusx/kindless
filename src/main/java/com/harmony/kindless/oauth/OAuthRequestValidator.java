package com.harmony.kindless.oauth;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;

/**
 * @author wuxii@foxmail.com
 */
public interface OAuthRequestValidator {

    void valid(OAuthRequest request) throws OAuthProblemException;

}
