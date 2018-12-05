package com.harmony.kindless.user.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.harmony.kindless.apis.domain.user.User;
import com.harmony.kindless.apis.domain.user.UserToken;
import com.harmony.kindless.apis.dto.UserTokenClaims;
import com.harmony.kindless.user.repository.UserTokenRepository;
import com.harmony.kindless.user.service.UserTokenService;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;
import com.harmony.umbrella.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author wuxii
 */
@Service
public class UserTokenServiceImpl extends ServiceSupport<UserToken, Long> implements UserTokenService {

    @Autowired
    private UserTokenRepository userTokenRepository;

    private int expiredInSeconds;
    private String algorithm;

    @Override
    public String generateTokenValue(UserTokenClaims utc) {
        Date now = new Date();
        Date expiredAt = expiresIn(expiredInSeconds);

        String tokenValue = JWT.create()
                .withJWTId(utc.getId())
                .withExpiresAt(expiredAt)
                .withNotBefore(now)
                .sign(Algorithm.HMAC512(algorithm));

        UserToken userToken = UserToken.builder()
                .expiredAt(expiredAt)
                .grantedAt(now)
                .userAgent(utc.getUserAgent())
                .ipAddress(utc.getIpAddress())
                .token(tokenValue)
                .user(new User(1l))
                .type(1)
                .build();
        saveOrUpdate(userToken);
        return tokenValue;
    }

    @Override
    protected QueryableRepository<UserToken, Long> getRepository() {
        return userTokenRepository;
    }

    @Override
    protected Class<UserToken> getDomainClass() {
        return UserToken.class;
    }

    private static final Date expiresIn(int expiresInSeconds) {
        return TimeUtils.addSeconds(new Date(), expiresInSeconds);
    }

}
