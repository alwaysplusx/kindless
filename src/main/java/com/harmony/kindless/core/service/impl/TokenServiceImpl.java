package com.harmony.kindless.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.harmony.kindless.core.domain.Token;
import com.harmony.kindless.core.repository.TokenRepository;
import com.harmony.kindless.core.service.TokenService;
import com.harmony.kindless.shiro.JwtToken;
import com.harmony.kindless.shiro.JwtToken.OriginClaims;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;

/**
 * @author wuxii@foxmail.com
 */
@Service
public class TokenServiceImpl extends ServiceSupport<Token, String> implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;
    // private int expiresIn = 7200;
    // private String issuer = "kindless";
    private boolean strictMode;

    public TokenServiceImpl() {
    }

    public TokenServiceImpl(boolean strictMode) {
        this.strictMode = strictMode;
    }

    @Override
    public boolean verify(JwtToken token) {
        Token expectToken = findOne(token.getToken());
        return verify(expectToken, token);
    }

    @Override
    public boolean verifyOrigin(JwtToken token) {
        return verifyOrigin(findOne(token.getToken()), token);
    }

    @Override
    public boolean verifySignature(JwtToken token) {
        return verifySignature(findOne(token.getToken()), token);
    }

    protected boolean verify(Token expectToken, JwtToken token) {
        return verifySignature(expectToken, token)//
                && (strictMode || verifyOrigin(expectToken, token));
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
        Token expectToken = findOne(token.getToken());
        if (verify(expectToken, token)) {
            return expectToken.getSessionId();
        }
        return null;
    }

    protected String parseAlgorithmKey(String key, String random) {
        return DigestUtils.md5DigestAsHex((key + random).getBytes());
    }

    // @Override
    // public Token grant(User user, TokenClaims claims) {
    // String username = user.getUsername();
    // String random = RandomStringUtils.randomAlphabetic(6);
    // String key = encodeKey(user.getPassword(), random);
    // try {
    // Algorithm algorithm = Algorithm.HMAC512(key);
    // String token = JWT.create()//
    // .withIssuedAt(new Date())//
    // .withIssuer(issuer)//
    // .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(expiresIn)))//
    // .withAudience(username)//
    // .sign(algorithm);
    // Token result = new Token();
    // result.setToken(token);
    // result.setUser(user);
    // result.setDevice(claims.getUserAgent());
    // result.setHost(claims.getRemoteAddr());
    // result.setRandom(random);
    // return saveOrUpdate(result);
    // } catch (Exception e) {
    // throw new AuthenticationException(e);
    // }
    // }

    protected String encodeKey(String key, String random) {
        return DigestUtils.md5DigestAsHex((key + random).getBytes());
    }

    @Override
    protected QueryableRepository<Token, String> getRepository() {
        return tokenRepository;
    }

}
