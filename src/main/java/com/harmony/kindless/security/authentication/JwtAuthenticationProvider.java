package com.harmony.kindless.security.authentication;

import com.harmony.kindless.security.IdentityUserDetails;
import com.harmony.kindless.security.IdentityUserDetailsService;
import com.harmony.kindless.security.jwt.JwtToken;
import com.harmony.kindless.security.jwt.JwtTokenDecoder;
import com.harmony.kindless.security.jwt.JwtTokenException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Collection;

/**
 * @author wuxii
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private IdentityUserDetailsService identityUserDetailsService;

    private JwtTokenDecoder jwtTokenDecoder;

    public JwtAuthenticationProvider() {
    }

    public JwtAuthenticationProvider(IdentityUserDetailsService identityUserDetailsService) {
        this.identityUserDetailsService = identityUserDetailsService;
    }

    public JwtAuthenticationProvider(IdentityUserDetailsService identityUserDetailsService, JwtTokenDecoder jwtTokenDecoder) {
        this.identityUserDetailsService = identityUserDetailsService;
        this.jwtTokenDecoder = jwtTokenDecoder;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // TODO filter的authentication token修改
        PreAuthenticatedAuthenticationToken token = (PreAuthenticatedAuthenticationToken) authentication;
        Object principal = token.getPrincipal();
        if (!(principal instanceof JwtToken)) {
            throw new JwtTokenException("unknown authentication principal");
        }
        JwtToken jwtToken = ((JwtToken) principal);
        Long uid = jwtToken.getUId();
        IdentityUserDetails userDetails = identityUserDetailsService.loadUserById(uid);
        if (userDetails == null) {
            throw new JwtTokenException("user not found");
        }
        return createSuccessAuthentication(jwtToken, userDetails);
    }

    protected Authentication createSuccessAuthentication(JwtToken jwtToken, IdentityUserDetails userDetails) {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        JwtAuthenticationToken result = new JwtAuthenticationToken(jwtToken, authorities);
        result.setAuthenticated(true);
        result.setDetails(userDetails);
        return result;
    }

    public IdentityUserDetailsService getIdentityUserDetailsService() {
        return identityUserDetailsService;
    }

    public void setIdentityUserDetailsService(IdentityUserDetailsService identityUserDetailsService) {
        this.identityUserDetailsService = identityUserDetailsService;
    }

    public JwtTokenDecoder getJwtTokenDecoder() {
        return jwtTokenDecoder;
    }

    public void setJwtTokenDecoder(JwtTokenDecoder jwtTokenDecoder) {
        this.jwtTokenDecoder = jwtTokenDecoder;
    }

}
