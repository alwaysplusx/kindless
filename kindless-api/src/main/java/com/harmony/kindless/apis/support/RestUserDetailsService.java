package com.harmony.kindless.apis.support;

import com.harmony.kindless.apis.TokenAuthenticationException;
import com.harmony.kindless.apis.clients.UserClient;
import com.harmony.umbrella.security.SecurityToken;
import com.harmony.umbrella.security.userdetails.SecurityTokenUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author wuxii
 */
public class RestUserDetailsService implements SecurityTokenUserDetailsService {

    private UserClient userClient;

    public RestUserDetailsService(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public UserDetails loadUserBySecurityToken(SecurityToken securityToken) {
        return userClient
                .getRestUserDetails(securityToken)
                .orElseThrow(TokenAuthenticationException::new);
    }

}
