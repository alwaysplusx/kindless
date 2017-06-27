package com.harmony.kindless.oauth.jwt;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author wuxii@foxmail.com
 */
public class JwtOAuthIssuer {

    private static final String key = "vUMNknPjYgxdxH4lZMXeKO7zioAqaYsJRYxFkulP0t0lft22JQ8uJ/XHvIz2ygZdU2uF/x7CvbVkBdqbmPLvAw==";

    public String generate() {
        return Jwts.builder()//
                .setIssuer("wuxii1")//
                .setIssuedAt(new Date())//
                .setExpiration(new Date(System.currentTimeMillis() + 7200 * 1000))//
                .setAudience("david1")//
                .signWith(SignatureAlgorithm.HS512, key)//
                .compact();
    }

    public static void main(String[] args) {
        String token = new JwtOAuthIssuer().generate();
        System.out.println(token);
    }

}
