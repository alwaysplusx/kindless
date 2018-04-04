package com.harmony.kindless.shiro.realm;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.harmony.kindless.core.domain.ClientInfo;
import com.harmony.kindless.core.domain.User;
import com.harmony.kindless.shiro.PrimaryPrincipal;

class SimplePrimaryPrincipal implements Serializable, PrimaryPrincipal {

    public static PrimaryPrincipalBuilder newBuilder() {
        return new PrimaryPrincipalBuilder();
    }

    public static PrimaryPrincipal build(User user, ClientInfo client, String token) {
        return newBuilder().setUser(user).setClient(client).setToken(token).build();
    }

    static class PrimaryPrincipalBuilder {

        private Long userId;
        private String username;
        private String nickname;
        private String clientId;
        private String token;
        private Map<String, Object> params = new HashMap<>();

        PrimaryPrincipalBuilder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        PrimaryPrincipalBuilder setClient(ClientInfo client) {
            setClientId(client.getClientId());
            return this;
        }

        PrimaryPrincipalBuilder setUser(User user) {
            setUsername(user.getUsername());
            setUserId(user.getUserId());
            setNickname(user.getNickname());
            return this;
        }

        PrimaryPrincipalBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        PrimaryPrincipalBuilder setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        PrimaryPrincipalBuilder setClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        PrimaryPrincipalBuilder setToken(String token) {
            this.token = token;
            return this;
        }

        PrimaryPrincipalBuilder setParam(String name, Object val) {
            this.params.put(name, val);
            return this;
        }

        PrimaryPrincipal build() {
            return new SimplePrimaryPrincipal(userId, username, nickname, clientId, token, params);
        }

    }

    private static final long serialVersionUID = 126866613052151787L;

    private final Long userId;
    private final String username;
    private final String nickname;
    private final String clientId;
    private final String token;
    private final Map<String, Object> params;

    public SimplePrimaryPrincipal(Long userId, String username, String nickname, String clientId, String token, Map<String, Object> params) {
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
        this.clientId = clientId;
        this.token = token;
        this.params = Collections.unmodifiableMap(params);
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public Object getParam(String name) {
        return params.get(name);
    }

}
