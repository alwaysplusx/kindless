package com.harmony.kindless.jjwt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.util.Base64Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author wuxii@foxmail.com
 */
public class JwtOAuthIssuer {

    private static final String key = "vUMNknPjYgxdxH4lZMXeKO7zioAqaYsJRYxFkulP0t0lft22JQ8uJ/XHvIz2ygZdU2uF/x7CvbVkBdqbmPLvAw==";
    private static Date date;

    static {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse("2017-01-01 00:00:01");
        } catch (ParseException e) {
            date = new Date();
        }
    }

    public String generate() {
        return Jwts.builder()//
                .setIssuer("wuxii1")//
                .setIssuedAt(new Date())//
                .setExpiration(new Date(System.currentTimeMillis() + 7200 * 1000))//
                .setAudience("david1")//
                .signWith(SignatureAlgorithm.HS512, key)//
                .compact();
    }

    @Test
    public void testJwt() {
        String jwt = Jwts.builder()//
                .setAudience("wuxii")//
                .setIssuer("kindless")//
                .setIssuedAt(date)//
                .setSubject("subject")//
                .setId("id")//
                .setNotBefore(date)//
                .setExpiration(date)//
                .signWith(SignatureAlgorithm.HS512, "abc123")//
                .compact();
        System.out.println(jwt);
        for (String s : jwt.split("\\.")) {
            System.out.println(new String(Base64Utils.decodeFromString(s)));
        }
    }

    @Test
    public void testJWT() throws Exception {
        String jwt = JWT.create()//
                .withAudience("wuxii")//
                .withIssuer("kindless")//
                .withIssuedAt(date)//
                .withSubject("subject")//
                .withJWTId("id")//
                .withNotBefore(date)//
                .withExpiresAt(date)//
                .sign(Algorithm.HMAC512("abc123"));//
        System.out.println(jwt);
        for (String s : jwt.split("\\.")) {
            System.out.println(new String(Base64Utils.decodeFromString(s)));
        }
    }

    public static void main(String[] args) {

        // String token = new JwtOAuthIssuer().generate();
        // System.out.println(token);
        // String uuid1 = UUID.randomUUID().toString();
        // String uuid2 = UUID.randomUUID().toString();
        // String all = uuid1 + uuid2;
        // System.out.println(all);
        // System.out.println(caseReverse(Base64Utils.encodeToString(uuid1.getBytes())));
        // System.out.println(caseReverse(Base64Utils.encodeToString(uuid2.getBytes())));
        // System.out.println(caseReverse(Base64Utils.encodeToString(all.getBytes())));

        System.out.println(RandomStringUtils.randomAlphanumeric(128));
        System.out.println(RandomStringUtils.randomNumeric(9));

    }

    public static String caseReverse(String s) {
        StringBuilder o = new StringBuilder();
        for (int i = 0, max = s.length(); i < max; i++) {
            char c = s.charAt(i);
            o.append(Character.isUpperCase(c) ? Character.toLowerCase(c) : Character.toUpperCase(c));
        }
        return o.toString();
    }

}
