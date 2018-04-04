package com.harmony.kindless.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.realm.AuthorizingRealm;

import com.harmony.kindless.jwt.JwtToken;
import com.harmony.kindless.shiro.authc.JwtAuthenticationToken;

public abstract class AbstractJwtRealm extends AuthorizingRealm {

    protected AbstractJwtRealm(String name) {
        this.setAuthenticationTokenClass(JwtAuthenticationToken.class);
    }

    @Override
    public final boolean supports(AuthenticationToken token) {
        return super.supports(token) && supports(((JwtAuthenticationToken) token).getJwtToken());
    }

    protected abstract boolean supports(JwtToken jwtToken);

    @Override
    protected final AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        return doGetAuthenticationInfo((JwtAuthenticationToken) token);
    }

    protected abstract AuthenticationInfo doGetAuthenticationInfo(JwtAuthenticationToken jwtToken) throws AuthenticationException;

}
