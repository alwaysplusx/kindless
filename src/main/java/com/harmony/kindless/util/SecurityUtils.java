package com.harmony.kindless.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.util.DigestUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.harmony.kindless.shiro.JwtToken;
import com.harmony.umbrella.context.ContextHelper;
import com.harmony.umbrella.context.ContextHelper.SessionContext;
import com.harmony.umbrella.context.CurrentContext.UserPrincipal;
import com.harmony.umbrella.util.StringUtils;

import io.jsonwebtoken.lang.Assert;

/**
 * @author wuxii@foxmail.com
 */
public class SecurityUtils {

    public static void applyUserPrincipal(UserPrincipal principal) {
        applyUserPrincipal(principal, getSubject().getSession());
    }

    public static void applyUserPrincipal(UserPrincipal principal, Subject subject) {
        Assert.notNull(subject, "subject must not null");
        applyUserPrincipal(principal, subject.getSession());
    }

    public static void applyUserPrincipal(UserPrincipal principal, Session session) {
        Assert.notNull(session, "sesssion must not null");
        Assert.notNull(principal, "principal must not null");
        ContextHelper.applyUserPrincipal(principal, new ShiroSessionContext(session));
    }

    public static void clearUserPrincipal() {
        clearUserPrincipal(getSubject());
    }

    public static void clearUserPrincipal(Subject subject) {
        Assert.notNull(subject, "subject must not null");
        clearUserPrincipal(subject.getSession());
    }

    public static void clearUserPrincipal(Session session) {
        ContextHelper.clearUserPrincipal(new ShiroSessionContext(session));
    }

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

    public static UserPrincipal getUserPrincipal() {
        return ContextHelper.getUserPrincipal();
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
        public ThridpartPrincipal getThridpartPrincipal() {
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

    public static class ShiroSessionContext implements SessionContext {

        private Session session;

        public ShiroSessionContext(Session session) {
            this.session = session;
        }

        @Override
        public Object get(String name) {
            return session.getAttribute(name);
        }

        @Override
        public void put(String name, Object val) {
            session.setAttribute(name, val);
        }

        @Override
        public Object remove(String name) {
            return session.removeAttribute(name);
        }

        @Override
        public List<String> getKeys() {
            Collection<Object> keys = session.getAttributeKeys();
            List<String> result = new ArrayList<>();
            for (Object key : keys) {
                if (key instanceof String) {
                    result.add((String) key);
                }
            }
            return result;
        }

    }

}
