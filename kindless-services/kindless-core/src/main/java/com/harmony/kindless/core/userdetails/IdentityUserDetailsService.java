package com.harmony.kindless.core.userdetails;

import com.harmony.umbrella.security.SecurityToken;
import com.harmony.umbrella.security.userdetails.IdentityUserDetails;
import com.harmony.umbrella.security.userdetails.SecurityTokenUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author wuxii
 */
public interface IdentityUserDetailsService extends UserDetailsService, SecurityTokenUserDetailsService {

	IdentityUserDetails loadUserBySecurityToken(SecurityToken securityToken) throws UsernameNotFoundException;

	IdentityUserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
