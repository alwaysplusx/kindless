package com.kindless.apis.feign;

/**
 * @author wuxii
 */
@FunctionalInterface
public interface AuthorizationTokenFinder {

    String findAuthorizationToken();

}
