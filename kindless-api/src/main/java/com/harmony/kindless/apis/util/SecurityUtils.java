package com.harmony.kindless.apis.util;

import com.harmony.kindless.apis.dto.UserSecurityData;
import com.harmony.umbrella.security.jwt.JwtUserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author wuxii
 */
public class SecurityUtils {

    public static JwtUserDetails buildUserDetails(UserSecurityData userSecurityData) {
        UserDetails user = User.builder()
                .username(userSecurityData.getUsername())
                .password(userSecurityData.getPassword())
                .accountExpired(userSecurityData.isAccountExpired())
                .accountLocked(!userSecurityData.isAccountEnabled())
                .credentialsExpired(userSecurityData.isPasswordExpired())
                .authorities(userSecurityData.getAuthorities().toArray(new String[0]))
                .build();
        return new JwtUserDetails(userSecurityData.getUserId(), user);
    }

}
