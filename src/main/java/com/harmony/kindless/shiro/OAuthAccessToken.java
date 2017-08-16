package com.harmony.kindless.shiro;

import org.apache.shiro.authc.RememberMeAuthenticationToken;

/**
 * @author wuxii@foxmail.com
 */
public class OAuthAccessToken implements RememberMeAuthenticationToken {

    private static final long serialVersionUID = -7350451568153093691L;

    private String username;
    private String accessToken;
    private boolean rememberMe;

    public OAuthAccessToken() {
    }

    public OAuthAccessToken(String username, String accessToken) {
        this.username = username;
        this.accessToken = accessToken;
    }

    public OAuthAccessToken(String username, String accessToken, boolean rememberMe) {
        this.username = username;
        this.accessToken = accessToken;
        this.rememberMe = rememberMe;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public Object getCredentials() {
        return accessToken;
    }

    @Override
    public boolean isRememberMe() {
        return rememberMe;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

}
