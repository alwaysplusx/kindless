package com.harmony.kindless.core.support;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.harmony.kindless.core.domain.Certificate;
import com.harmony.kindless.core.domain.ClientInfo;
import com.harmony.kindless.core.domain.User;
import com.harmony.kindless.core.service.CertificateService;
import com.harmony.kindless.jwt.JwtToken;
import com.harmony.kindless.jwt.JwtTokenService;
import com.harmony.kindless.jwt.RequestOriginProperties;
import com.harmony.kindless.util.SecurityUtils;

/**
 * @author wuxii@foxmail.com
 */
@Component
public class KindlessSecurityService implements SecurityService, JwtTokenService {

    @Autowired
    private CertificateService certificateService;

    private boolean strictMode = true;
    private int expiresIn = 7200;
    private String issuer = "kindless";

    @Override
    public Certificate grant(User user, RequestOriginProperties rop) {
        if (user == null) {
            throw new IllegalArgumentException("user not allow null");
        }
        return doGrant(user, null, rop);
    }

    @Override
    public Certificate grant(User user, ClientInfo ci, RequestOriginProperties rop) {
        if (user == null) {
            throw new IllegalArgumentException("resource owner not allow null");
        }
        if (ci == null) {
            throw new IllegalArgumentException("the third-part client info not allow null");
        }
        return doGrant(user, ci, rop);
    }

    @Override
    public Certificate grant(ClientInfo ci, RequestOriginProperties rop) {
        if (ci == null) {
            throw new IllegalArgumentException("the client info not allow null");
        }
        return doGrant(null, ci, rop);
    }

    protected Certificate doGrant(User user, ClientInfo ci, RequestOriginProperties rop) {
        String random = generateRandomString();
        String key = user == null ? ci.getClientSecret() : user.getPassword();
        String token = null;
        try {
            Algorithm algorithm = parseAlgorithm(key, random);
            token = buildJwtToken(user == null ? null : user.getUsername(), ci == null ? null : ci.getClientId(), algorithm);
        } catch (Exception e) {
            throw new AuthenticationException(e);
        }
        if (token == null) {
            throw new AuthenticationException("authentication token sign failed");
        }
        Certificate result = new Certificate();
        result.setToken(token);
        result.setRandom(random);
        if (user != null) {
            result.setUser(user);
        }
        if (ci != null) {
            result.setClientInfo(ci);
        }
        if (rop != null) {
            result.setDevice(rop.getDevice());
            result.setHost(rop.getHost());
        }
        Session session = SecurityUtils.getSubject().getSession(false);
        if (session != null) {
            result.setSessionId((String) session.getId());
        }
        return certificateService.saveOrUpdate(result);
    }

    // jwt service methods

    @Override
    public void bindSessionId(JwtToken token, String sessionId) {
        certificateService.bindSessionId(token.getToken(), sessionId);
    }

    @Override
    public String getSessionId(JwtToken token) {
        return certificateService.getSessionId(token.getToken());
    }

    @Override
    public boolean verify(JwtToken token) {
        Certificate expect = certificateService.findByToken(token.getToken());
        return expect != null && doVerify(expect, token);
    }

    @Override
    public boolean verifyOrigin(JwtToken token) {
        Certificate expect = certificateService.findByToken(token.getToken());
        return doVerifyOrigin(expect, token);
    }

    @Override
    public boolean verifySignature(JwtToken token) {
        Certificate expect = certificateService.findByToken(token.getToken());
        return doVerifySignature(expect, token);
    }

    protected boolean doVerify(Certificate expect, JwtToken token) {
        return doVerifySignature(expect, token)//
                && (strictMode || doVerifyOrigin(expect, token));
    }

    protected boolean doVerifySignature(Certificate expect, JwtToken token) {
        if (expect == null) {
            return false;
        }
        String random = expect.getRandom();
        String key = expect.getUser() == null//
                ? expect.getClientInfo().getClientSecret() //
                : expect.getUser().getPassword();
        try {
            Algorithm algorithm = parseAlgorithm(key, random);
            JWT.require(algorithm)//
                    .build()//
                    .verify(token.getToken());
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    protected boolean doVerifyOrigin(Certificate expect, JwtToken token) {
        if (expect == null) {
            return false;
        }
        RequestOriginProperties rop = token.getRequestOriginProperties();
        return rop != null //
                && ObjectUtils.nullSafeEquals(expect.getDevice(), rop.getDevice()) //
                && ObjectUtils.nullSafeEquals(expect.getHost(), rop.getHost());
    }

    protected String generateRandomString() {
        return RandomStringUtils.randomAlphanumeric(16);
    }

    protected String buildJwtToken(String user, String client, Algorithm algorithm) {
        Builder builder = JWT.create()//
                .withIssuedAt(new Date())//
                .withIssuer(issuer)//
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(expiresIn)));
        if (user != null) {
            builder.withAudience(user);
        }
        if (client != null) {
            builder.withClaim("client", client);
        }
        return builder.sign(algorithm);
    }

    protected Algorithm parseAlgorithm(String key, String random) throws IllegalArgumentException, UnsupportedEncodingException {
        return Algorithm.HMAC512(SecurityUtils.parseAlgorithmKey(key, random));
    }

    public boolean isStrictMode() {
        return strictMode;
    }

    public void setStrictMode(boolean strictMode) {
        this.strictMode = strictMode;
    }
}
