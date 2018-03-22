package com.harmony.kindless.core.service.impl;

import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.harmony.kindless.core.domain.Token;
import com.harmony.kindless.core.repository.TokenRepository;
import com.harmony.kindless.core.service.TokenService;
import com.harmony.kindless.shiro.JwtToken;
import com.harmony.kindless.shiro.JwtToken.OriginClaims;
import com.harmony.kindless.util.SecurityUtils;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;

/**
 * @author wuxii@foxmail.com
 */
@Service
public class TokenServiceImpl extends ServiceSupport<Token, String> implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;
    private boolean strictMode;

    public TokenServiceImpl() {
    }

    public TokenServiceImpl(boolean strictMode) {
        this.strictMode = strictMode;
    }

    @Override
    public boolean verify(JwtToken token) {
        Token expectToken = findById(token.getToken());
        return verify(expectToken, token);
    }

    protected boolean verify(Token expectToken, JwtToken token) {
        return verifySignature(expectToken, token)//
                && (strictMode || verifyOrigin(expectToken, token));
    }

    @Override
    public boolean verifyOrigin(JwtToken token) {
        return verifyOrigin(findById(token.getToken()), token);
    }

    @Override
    public boolean verifySignature(JwtToken token) {
        return verifySignature(findById(token.getToken()), token);
    }

    protected boolean verifySignature(Token expectToken, JwtToken token) {
        if (expectToken == null) {
            return false;
        }
        String password = expectToken.getUser().getPassword();
        String key = parseAlgorithmKey(password, expectToken.getRandom());
        try {
            JWT.require(Algorithm.HMAC512(key))//
                    .build()//
                    .verify(token.getToken());
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    protected boolean verifyOrigin(Token expectToken, JwtToken token) {
        if (expectToken == null) {
            return false;
        }
        OriginClaims claims = token.getOriginClaims();
        return claims != null //
                && ObjectUtils.nullSafeEquals(expectToken.getDevice(), claims.getDevice()) //
                && ObjectUtils.nullSafeEquals(expectToken.getHost(), claims.getHost());
    }

    @Override
    public String getSessionId(JwtToken token) {
        Token expectToken = findById(token.getToken());
        if (token.isExpired()) {
            throw new AuthenticationException("token expired");
        }
        if (verify(expectToken, token)) {
            return expectToken.getSessionId();
        }
        return null;
    }

    protected String parseAlgorithmKey(String key, String random) {
        return SecurityUtils.parseAlgorithmKey(key, random);
    }

    @Override
    protected QueryableRepository<Token, String> getRepository() {
        return tokenRepository;
    }

}
