package com.harmony.kindless.shiro.support;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.realm.Realm;

/**
 * @author wuxii@foxmail.com
 */
public class FailedFocusAuthenticationStrategy extends AtLeastOneSuccessfulStrategy {

    @Override
    public AuthenticationInfo afterAttempt(Realm realm, AuthenticationToken token, AuthenticationInfo singleRealmInfo, AuthenticationInfo aggregateInfo,
            Throwable t) throws AuthenticationException {
        if (t instanceof AuthenticationException) {
            throw (AuthenticationException) t;
        }
        return super.afterAttempt(realm, token, singleRealmInfo, aggregateInfo, t);
    }

    @Override
    public AuthenticationInfo afterAllAttempts(AuthenticationToken token, AuthenticationInfo aggregate) throws AuthenticationException {
        return super.afterAllAttempts(token, aggregate);
    }

}
