package com.harmony.kindless.util;

import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.springframework.util.DigestUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.harmony.kindless.shiro.JwtToken;
import com.harmony.umbrella.util.StringUtils;

/**
 * @author wuxii@foxmail.com
 */
public class SecurityUtils {

    public static String parseAlgorithmKey(String... s) {
        StringBuilder o = new StringBuilder();
        for (String t : s) {
            o.append(t);
        }
        return DigestUtils.md5DigestAsHex((o.toString()).getBytes());
    }

    public static Subject getSubject() {
        return org.apache.shiro.SecurityUtils.getSubject();
    }

    public static JwtToken getRequestToken(HttpServletRequest request, String name) {
        String s = request.getHeader(name);
        return StringUtils.isNotBlank(s) ? new JwtTokenImpl(s, request) : null;
    }

    private static class JwtTokenImpl implements JwtToken, Serializable {

        private static final long serialVersionUID = 7631811951212850309L;

        private String token;
        private String username;
        private DecodedJWT jwt;
        private HttpServletRequest request;

        public JwtTokenImpl(String token, HttpServletRequest request) {
            this.request = request;
            this.setToken(token);
        }

        @Override
        public String getToken() {
            return token;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public boolean isExpired() {
            Date expiresAt = jwt.getExpiresAt();
            return expiresAt != null && System.currentTimeMillis() > expiresAt.getTime();
        }

        @Override
        public ThridpartPrincipal geThridpartPrincipal() {
            String client = getStringClaim("client");
            return client != null //
                    ? new ThridpartPrincipal(username, client, getStringClaim("scope")) //
                    : null;
        }

        @Override
        public OriginClaims getOriginClaims() {
            return new OriginClaims(request);
        }

        protected String getStringClaim(String name) {
            Claim claim = jwt.getClaim(name);
            return claim != null ? claim.asString() : null;
        }

        private void setToken(String token) {
            this.token = token;
            this.jwt = JWT.decode(token);
            if (jwt.getAudience() != null && !jwt.getAudience().isEmpty()) {
                this.username = jwt.getAudience().get(0);
            }
        }

    }

}
