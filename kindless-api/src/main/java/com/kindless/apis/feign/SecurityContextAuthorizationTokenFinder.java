package com.kindless.apis.feign;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author wuxii
 */
public class SecurityContextAuthorizationTokenFinder implements AuthorizationTokenFinder {

    @Override
    public String findAuthorizationToken() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        return null;
    }

}
