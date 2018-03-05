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
        Token result = tokenService.findOne(token.getToken());
        String sessionId = (String) subject.getSession().getId();
        result.setSessionId(sessionId);
        return tokenService.saveOrUpdate(result);
    }

    @Override
    public Token grant(ThridpartPrincipal tpp, OriginClaims claims) {
        String client = tpp.getClient();
        String username = tpp.getUsername();
        if (StringUtils.isBlank(client) || StringUtils.isBlank(username)) {
            throw new AuthenticationException("invalid third-part principal");
        }
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new AuthenticationException("reference user not found");
        }
        String random = RandomStringUtils.randomAlphabetic(8);
        String key = parseAlgorithmKey(user.getPassword(), random);
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC512(key);
            Builder builder = JWT.create();//
            builder.withIssuedAt(new Date())//
                    .withIssuer(issuer)//
                    .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(expiresIn)))//
                    .withAudience(username);//
            if (tpp.getClient() != null) {
                builder.withClaim("client", tpp.getClient());
            }
            if (tpp.getScope() != null) {
                builder.withClaim("scope", tpp.getScope());
            }
            token = builder.sign(algorithm);
        } catch (Exception e) {
        }
        Token result = new Token();
        result.setToken(token);
        result.setUser(userService.findByUsername(username));
        result.setDevice(claims.getDevice());
        result.setHost(claims.getHost());
        result.setRandom(random);
        return tokenService.saveOrUpdate(result);
    }

    protected Token grant(String username, String password, Subject subject, OriginClaims claims) {
        String random = RandomStringUtils.randomAlphabetic(8);
        String key = parseAlgorithmKey(password, random);
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC512(key);
            token = JWT.create()//
                    .withIssuedAt(new Date())//
                    .withIssuer(issuer)//
                    .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(expiresIn)))//
                    .withAudience(username)//
                    .sign(algorithm);
        } catch (Exception e) {
            throw new AuthenticationException(e);
        }
        if (token == null) {
            throw new AuthenticationException("authentication token sign failed");
        }
        Token result = new Token();
        result.setToken(token);
        result.setUser(userService.findByUsername(username));
        result.setDevice(claims.getDevice());
        result.setHost(claims.getHost());
        result.setRandom(random);
        result.setSessionId((String) subject.getSession().getId());
        return tokenService.saveOrUpdate(result);
    }

    protected String parseAlgorithmKey(String key, String random) {
        return SecurityUtils.parseAlgorithmKey(key, random);
    }

}
