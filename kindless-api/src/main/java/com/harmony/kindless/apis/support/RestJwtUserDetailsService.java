package com.harmony.kindless.apis.support;

import com.harmony.kindless.apis.clients.UserClient;
import com.harmony.kindless.apis.util.SecurityUtils;
import com.harmony.umbrella.security.jwt.JwtUserDetails;
import com.harmony.umbrella.security.jwt.JwtUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author wuxii
 */
public class RestJwtUserDetailsService implements JwtUserDetailsService, UserDetailsService {

    private UserClient userClient;

    public RestJwtUserDetailsService(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return SecurityUtils.buildUserDetails(userClient.getUserSecurityData(username));
    }

    @Override
    public JwtUserDetails loadUserById(Long userId) {
        return SecurityUtils.buildUserDetails(userClient.getUserSecurityData(userId));
    }

}
