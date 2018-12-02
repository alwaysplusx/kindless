package com.harmony.kindless.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;

/**
 * @author wuxii
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final Jwt jwt;

    public JwtAuthenticationToken(Jwt jwt) {
        this(jwt, Collections.emptyList());
    }

    public JwtAuthenticationToken(Jwt jwt, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.jwt = jwt;
    }

    @Override
    public Object getPrincipal() {
        return jwt.getId();
    }

    @Override
    public Object getCredentials() {
        return jwt;
    }

}
