package com.harmony.kindless.jjwt;

import java.security.Key;

import org.junit.Test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

/**
 * @author wuxii@foxmail.com
 */
public class TokenTest {

    static final String issuer = "wuxii";
    static Key key = MacProvider.generateKey();

    @Test
    public void testJjwt() {
        String token = Jwts.builder()//
                .setIssuer(issuer)//
                .signWith(SignatureAlgorithm.HS512, key.getEncoded())//
                .compact();
        System.out.println("jjwt: " + token);
        Jws<Claims> jws = Jwts.parser()//
                .setSigningKey(key)//
                .parseClaimsJws(token);
        System.out.println(jws.getBody().getIssuer());
    }

    @Test
    public void testAuth0() throws Exception {
        Algorithm algorithm = Algorithm.HMAC512(key.getEncoded());
        String token = JWT.create()//
                .withIssuer(issuer)//
                .sign(algorithm);
        System.out.println("auth0: " + token);
        // 不需要加密签名的密钥??
        DecodedJWT jwt = JWT.decode(token);
        System.out.println("auth0: " + jwt.getIssuer());

    }

}
