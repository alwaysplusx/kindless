package com.harmony.kindless.security.support;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.harmony.kindless.security.IdentityUserDetails;
import com.harmony.kindless.security.jwt.JwtToken;
import com.harmony.kindless.security.jwt.JwtTokenDecoder;
import com.harmony.kindless.security.jwt.JwtTokenGenerator;
import com.harmony.umbrella.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.jwt.JwtException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author wuxii
 */
public class Auth0JwtTokenHandler implements JwtTokenGenerator, JwtTokenDecoder {

    private static final Logger log = LoggerFactory.getLogger(Auth0JwtTokenHandler.class);

    private int expiresIn = 7200;

    private String signature;

    private StringRedisTemplate stringRedisTemplate;

    private String tokenCacheKeyPrefix = "token:jwt:";

    private boolean strictMode;

    private String issuer = "kindless";

    @Override
    public String generate(IdentityUserDetails userDetails, HttpServletRequest request) {
        String username = userDetails.getUsername();
        Date now = new Date();
        Date expiresTime = TimeUtils.addSeconds(now, expiresIn);
        Long uid = userDetails.getId();
        String tokenValue = JWT.create()
                .withIssuer(issuer)
                .withAudience(username)
                .withIssuedAt(new Date())
                .withExpiresAt(expiresTime)
                .withClaim("uid", uid)
                .withNotBefore(now)
                .sign(Algorithm.HMAC512(getNotNullSignature()));
        saveToCache(uid, tokenValue);
        return tokenValue;
    }

    @Override
    public JwtToken decode(String token) throws JwtException {
        DecodedJWT jwt = JWT
                .require(Algorithm.HMAC512(getNotNullSignature()))
                .build()
                .verify(token);
        return new JwtToken(jwt);
    }

    protected void saveToCache(Long uid, String tokenValue) {
        if (stringRedisTemplate != null) {
            String cacheKey = tokenCacheKeyPrefix + uid;
            stringRedisTemplate.opsForValue().set(cacheKey, tokenValue);
            stringRedisTemplate.expire(cacheKey, expiresIn, TimeUnit.SECONDS);
        }
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public StringRedisTemplate getStringRedisTemplate() {
        return stringRedisTemplate;
    }

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private String getNotNullSignature() {
        if (signature == null) {
            signature = UUID.randomUUID().toString();
            log.warn("未指定jwt的签名, 使用自动生成的签名替代: {}", signature);
        }
        return signature;
    }

    public boolean isStrictMode() {
        return strictMode;
    }

    public void setStrictMode(boolean strictMode) {
        this.strictMode = strictMode;
    }

    public void setTokenCacheKeyPrefix(String tokenCacheKeyPrefix) {
        this.tokenCacheKeyPrefix = tokenCacheKeyPrefix;
    }
}
