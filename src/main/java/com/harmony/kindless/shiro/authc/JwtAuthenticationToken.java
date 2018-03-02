package com.harmony.kindless.shiro.authc;

import org.apache.shiro.authc.AuthenticationToken;

import com.harmony.kindless.shiro.JwtToken;

/**
 * @author wuxii@foxmail.com
 */
public class JwtAuthenticationToken implements AuthenticationToken {

    private static final long serialVersionUID = 1792478236665798355L;
    private JwtToken jwtToken;

    public JwtAuthenticationToken() {
    }

    public JwtAuthenticationToken(JwtToken jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    public Object getPrincipal() {
        return jwtToken.getUsername();
    }

    @Override
    public Object getCredentials() {
        return jwtToken.getToken();
    }

    public JwtToken getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(JwtToken jwtToken) {
        this.jwtToken = jwtToken;
    }

}
