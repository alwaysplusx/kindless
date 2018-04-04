package com.harmony.kindless.shiro.authc;

import com.harmony.kindless.jwt.JwtToken;
import org.apache.shiro.authc.AuthenticationToken;

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
