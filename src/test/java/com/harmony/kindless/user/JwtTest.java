package com.harmony.kindless.user;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoderJwkSupport;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wuxii
 */
public class JwtTest {

    public static void main(String[] args) {
        String jwtkSetUrl = "";
        String tokenValue = "";

        Instant issuedAt = Instant.now();
        Instant expiresAt = Instant.now();
        Map<String, Object> headers = new HashMap<>();
        Map<String, Object> claims = new HashMap<>();
        Jwt jwt = new Jwt(tokenValue, issuedAt, expiresAt, headers, claims);
        JwtDecoder jwtDecoder = new NimbusJwtDecoderJwkSupport(jwtkSetUrl);
    }

}
