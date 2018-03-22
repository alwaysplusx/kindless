package com.harmony.kindless.core.service.impl;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.harmony.kindless.core.domain.Token;
import com.harmony.kindless.core.domain.User;
import com.harmony.kindless.core.service.SecurityService;
import com.harmony.kindless.core.service.TokenService;
import com.harmony.kindless.core.service.UserService;
import com.harmony.kindless.oauth.domain.ClientInfo;
import com.harmony.kindless.shiro.JwtToken;
import com.harmony.kindless.shiro.JwtToken.OriginClaims;
import com.harmony.kindless.shiro.JwtToken.ThridpartPrincipal;
import com.harmony.kindless.shiro.authc.JwtAuthenticationToken;
import com.harmony.kindless.util.SecurityUtils;
import com.harmony.umbrella.util.StringUtils;

/**
 * @author wuxii@foxmail.com
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    private int expiresIn = 7200;
    private String issuer = "kindless";

    public SecurityServiceImpl() {
    }

    @Override
    public User findUser(String username) {
        return userService.findByUsername(username);
    }

    @Override
    public String getSessionId(JwtToken token) {
        return tokenService.getSessionId(token);
    }

    @Override
    public boolean verify(JwtToken token) {
        return tokenService.verify(token);
    }

    @Override
    public Token login(String username, String password, OriginClaims claims) {
        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken(username, password));
        return grant(username, password, subject, claims);
    }

    @Override
    public Token login(JwtToken token) {
        Subject subject = SecurityUtils.getSubject();
        subject.login(new JwtAuthenticationToken(token));
        // update token session id
        Token result = tokenService.findById(token.getToken());
        String sessionId = (String) subject.getSession().getId();
        result.setSessionId(sessionId);
        return tokenService.saveOrUpdate(result);
    }

    @Override
    public Token grant(ThridpartPrincipal tpp, int expiresIn, OriginClaims claims) {
        // 第三方授权包含:
        // 1. 用户(资源拥有者)给第三方客户端授权
        // 2. 系统(当前的应用)直接给第三方授权
        String client = tpp.getClient();
        if (StringUtils.isBlank(client)) {
            throw new AuthenticationException("invalid third-part principal");
        }

        String username = tpp.getUsername();
        User user = null;
        if (username == null || (user = userService.findByUsername(username)) == null) {
            throw new AuthenticationException("resource owner " + username + " not found");
        }

        String random = RandomStringUtils.randomAlphabetic(8);
        String key = parseAlgorithmKey(user.getPassword(), random);
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC512(key);
            Builder builder = createAndInitBuilder(expiresIn, username);
            if (tpp.getClient() != null) {
                builder.withClaim("client", tpp.getClient());
            }
            if (tpp.getScope() != null) {
                builder.withClaim("scope", tpp.getScope());
            }
            token = builder.sign(algorithm);
        } catch (Exception e) {
            throw new AuthenticationException(e);
        }

        Token result = new Token();
        result.setToken(token);
        result.setUser(user);
        if (claims != null) {
            result.setDevice(claims.getDevice());
            result.setHost(claims.getHost());
        }
        result.setRandom(random);
        return tokenService.saveOrUpdate(result);
    }

    protected Token grant(String username, String password, Subject subject, OriginClaims claims) {
        String random = RandomStringUtils.randomAlphabetic(8);
        String key = parseAlgorithmKey(password, random);
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC512(key);
            Builder builder = createAndInitBuilder(expiresIn, username);
            token = builder.sign(algorithm);
        } catch (Exception e) {
            throw new AuthenticationException(e);
        }
        if (token == null) {
            throw new AuthenticationException("authentication token sign failed");
        }

        Token result = new Token();
        result.setToken(token);
        result.setUser(userService.findByUsername(username));
        if (claims != null) {
            result.setDevice(claims.getDevice());
            result.setHost(claims.getHost());
        }
        result.setRandom(random);
        result.setSessionId((String) subject.getSession().getId());
        return tokenService.saveOrUpdate(result);
    }

    protected Builder createAndInitBuilder(int expiresIn, String audience) {
        return JWT.create()//
                .withIssuedAt(new Date())//
                .withIssuer(issuer)//
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(expiresIn)))//
                .withAudience(audience);
    }

    /**
     * 根据资源拥有者的资源授予情况给予授权
     * 
     * @param resourceOwner
     *            资源拥有者
     * @param tpp
     *            第三方
     * @return
     */
    protected Token grantTokenByResourceOwner(User resourceOwner, ClientInfo ci) {
        return null;
    }

    protected String parseAlgorithmKey(String key, String random) {
        return SecurityUtils.parseAlgorithmKey(key, random);
    }

}
