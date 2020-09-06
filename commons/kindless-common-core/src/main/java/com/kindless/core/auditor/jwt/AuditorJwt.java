package com.kindless.core.auditor.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kindless.core.auditor.Auditor;
import lombok.Builder;

import java.time.Duration;
import java.util.Date;

/**
 * @author wuxin
 */
@Builder(setterPrefix = "set")
public class AuditorJwt {

    private String secret;

    @Builder.Default
    private Duration expiresIn = Duration.ofHours(2);

    public String generate(Auditor auditor) {
        Date now = new Date();
        long expiresAt = now.getTime() + expiresIn.toMillis();
        return JWT
                .create()
                .withJWTId(auditor.getUserId().toString())
                .withSubject(auditor.getUsername())
                .withExpiresAt(new Date(expiresAt))
                .withNotBefore(now)
                .sign(Algorithm.HMAC512(secret));
    }

    public Auditor parse(String token) {
        try {
            DecodedJWT jwt = JWT
                    .require(Algorithm.HMAC512(secret))
                    .build()
                    .verify(token);
            return Auditor
                    .builder()
                    .setTenantId(null)
                    .setUserId(Long.parseLong(jwt.getId()))
                    .setUsername(jwt.getSubject())
                    .build();
        } catch (JWTVerificationException e) {
            throw new IllegalStateException("token decode failed", e);
        }
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setExpiresIn(Duration expiresIn) {
        this.expiresIn = expiresIn;
    }

}
