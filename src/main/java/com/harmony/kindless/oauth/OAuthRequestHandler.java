package com.harmony.kindless.oauth;

import java.io.IOException;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * @author wuxii@foxmail.com
 */
public interface OAuthRequestHandler {

    boolean support(GrantType grantType);

    void handle(OAuthRequest request, NativeWebRequest webRequest) throws OAuthSystemException, IOException;

}
