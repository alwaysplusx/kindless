package com.harmony.kindless;

import static org.junit.Assert.*;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.Map;

import org.junit.Test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author wuxii@foxmail.com
 */
public class JwtTest {

    private static final String plainTextKey = "123456";

    private static final String key = new String(Base64.getEncoder().encode(plainTextKey.getBytes()));

    public static void main(String[] args) {
        System.out.println(key);

        String token = Jwts.builder()//
                .setSubject("for chrome")//
                .setAudience("wuxii")//
                .setIssuedAt(new Date())//
                .setIssuer("kindless")//
                .signWith(SignatureAlgorithm.HS512, key)//
                .compact();

        System.out.println(token);

        Decoder decoder = Base64.getDecoder();
        String[] tokens = token.split("\\.");
        for (String t : tokens) {
            try {
                System.out.println(new String(decoder.decode(t)));
            } catch (Exception e) {
            }
        }

        Jwt jwt = Jwts.parser().setSigningKey(key).parse(token);
        System.out.println(jwt);
    }

    @Test
    public void auth0JwtTest() throws Exception {
        String token = JWT.create()//
                .withSubject("jwt.io")//
                .withAudience("wuxii")//
                .withIssuedAt(new Date())//
                .withIssuer("kindless")//
                .sign(Algorithm.HMAC512(plainTextKey));

        Jwt jwt1 = decode_jwt$io(token);
        DecodedJWT jwt2 = decode_jsonwebtoken$io(token);

        assertEquals(((Map<String, Object>) jwt1.getBody()).get("iss"), jwt2.getIssuer());

    }

    @Test
    public void jsonwebtokenTest() {

        String token = Jwts.builder()//
                .setSubject("jsonwebtoken.io")//
                .setAudience("wuxii")//
                .setIssuedAt(new Date())//
                .setIssuer("kindless")//
                .signWith(SignatureAlgorithm.HS512, key)//
                .compact();

        Jwt jwt1 = decode_jwt$io(token);
        DecodedJWT jwt2 = decode_jsonwebtoken$io(token);

        assertEquals("kindless", ((Map<String, Object>) jwt1.getBody()).get("iss"));
        assertEquals("kindless", jwt2.getIssuer());

    }

    public Jwt decode_jwt$io(String token) {
        return Jwts.parser()//
                .setSigningKey(key)//
                .parse(token);
    }

    public DecodedJWT decode_jsonwebtoken$io(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(plainTextKey);
            JWTVerifier verifier = JWT//
                    .require(algorithm)//
                    .build();
            return verifier.verify(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
