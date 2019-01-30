package com.harmony.kindless.core.security;

import com.harmony.kindless.apis.dto.UserSecurityData;
import com.harmony.kindless.apis.util.SecurityUtils;
import com.harmony.kindless.core.service.UserService;
import com.harmony.umbrella.security.jwt.JwtUserDetails;
import com.harmony.umbrella.security.jwt.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author wuxii
 */
@Component
public class KindlessJwtUserDetailsService implements JwtUserDetailsService, UserDetailsService {

    private final UserService userService;

    @Autowired
    public KindlessJwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public JwtUserDetails loadUserById(Long uid) {
        UserSecurityData userSecurityData = userService.getUserSecurityData(uid, null);
        return SecurityUtils.buildUserDetails(userSecurityData);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserSecurityData userSecurityData = userService.getUserSecurityData(null, username);
        return SecurityUtils.buildUserDetails(userSecurityData);
    }

}
