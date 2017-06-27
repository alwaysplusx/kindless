package com.harmony.kindless.oauth.issuer;

/**
 * @author wuxii@foxmail.com
 */
public interface OAuthIssuer {

    String authorizationCode(String payload);

    String accessToken(String payload);

    String refreshToken(String payload);

}
