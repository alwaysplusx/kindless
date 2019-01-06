package com.harmony.kindless.security;

/**
 * @author wuxii
 */
public interface IdentityUserDetailsService {

    IdentityUserDetails loadUserById(Long userId);

}
