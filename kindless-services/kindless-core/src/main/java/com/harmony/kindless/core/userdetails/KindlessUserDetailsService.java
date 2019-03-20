package com.harmony.kindless.core.userdetails;

import com.harmony.kindless.apis.ResponseCodes;
import com.harmony.kindless.apis.TokenAuthenticationException;
import com.harmony.kindless.apis.domain.core.User;
import com.harmony.kindless.apis.support.RestUserDetails;
import com.harmony.kindless.core.service.UserAuthorityService;
import com.harmony.kindless.core.service.UserService;
import com.harmony.umbrella.security.SecurityToken;
import com.harmony.umbrella.security.jwt.JwtToken;
import com.harmony.umbrella.security.jwt.JwtTokenDecoder;
import com.harmony.umbrella.security.userdetails.IdentityUserDetails;
import com.harmony.umbrella.web.ResponseDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

/**
 * @author wuxii
 */
@Component
public class KindlessUserDetailsService implements IdentityUserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthorityService userAuthorityService;

    @Autowired
    private JwtTokenDecoder jwtTokenDecoder;

    @Override
    public IdentityUserDetails loadUserByUsername(String username) {
        return getUserDetails(username);
    }

    @Override
    public IdentityUserDetails loadUserBySecurityToken(SecurityToken securityToken) throws AuthenticationException {
        try {
            String token = securityToken.getToken();
            JwtToken jwtToken = jwtTokenDecoder.decode(token);
            return getUserDetails(jwtToken.getName());
        } catch (Exception e) {
            ResponseDetails r = e instanceof ResponseDetails ? (ResponseDetails) e : ResponseCodes.TOKEN_INCORRECT;
            throw new TokenAuthenticationException(r, e);
        }
    }

    private RestUserDetails getUserDetails(String username) {
        if (StringUtils.isBlank(username)) {
            throw ResponseCodes.ILLEGAL_ARGUMENT.toException();
        }
        RestUserDetails userDetails = getUserDetailsFromCache(username);
        if (userDetails == null) {
            User user = userService.getByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("user not found");
            }
            userDetails = buildRestUserDetails(user);
        }
        return userDetails;
    }

    protected RestUserDetails getUserDetailsFromCache(String username) {
        // TODO load user details from cache
        return null;
    }

    private RestUserDetails buildRestUserDetails(User user) {
        long now = System.currentTimeMillis();
        List<String> userAuthorities = userAuthorityService.getUserAuthorities(user.getId());
        return RestUserDetails
                .builder()
                .userId(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .accountNonExpired(now < user.getAccountExpiredAt().getTime())
                .credentialsNonExpired(now < user.getPasswordExpiredAt().getTime())
                .accountNonLocked(!user.isLocked())
                .enabled(user.isEnabled())
                .plainTextAuthorities(new HashSet<>(userAuthorities))
                .build();
    }

}
