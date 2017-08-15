package com.harmony.kindless.util;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;

/**
 * @author wuxii@foxmail.com
 */
public class SecurityUtils {

    public static void login(AuthenticationToken token, LoginCallback callback) throws AuthenticationException {
        Subject subject = null;
        Exception exception = null;
        try {
            subject = org.apache.shiro.SecurityUtils.getSubject();
            subject.login(token);
        } catch (Exception e) {
            exception = e;
        } finally {
            callback.run(subject, token, exception);
        }
    }

    public static void logout() {
        Subject subject = org.apache.shiro.SecurityUtils.getSubject();
        subject.logout();
    }

    public interface LoginCallback {

        void run(Subject subject, AuthenticationToken token, Exception exception);

    }

}
