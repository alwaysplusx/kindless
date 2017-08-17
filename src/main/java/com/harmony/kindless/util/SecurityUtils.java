package com.harmony.kindless.util;

import static com.harmony.umbrella.context.CurrentContext.*;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.harmony.kindless.core.domain.User;
import com.harmony.kindless.core.service.UserService;
import com.harmony.kindless.oauth.domain.AccessToken;
import com.harmony.kindless.oauth.domain.ClientInfo;
import com.harmony.kindless.oauth.service.AccessTokenService;
import com.harmony.kindless.shiro.OAuthAccessToken;
import com.harmony.umbrella.context.ContextHelper;
import com.harmony.umbrella.context.CurrentContext;

/**
 * @author wuxii@foxmail.com
 */
public class SecurityUtils {

    public static final String CLIENT_ID = CurrentContext.class.getName() + ".CLIENT_ID";
    public static final String ACCESS_TOKEN = CurrentContext.class.getName() + ".ACCESS_TOKEN";

    public static void login(AuthenticationToken token) {
        org.apache.shiro.SecurityUtils.getSubject().login(token);
    }

    public static void login(AuthenticationToken token, LoginCallback callback) throws AuthenticationException {
        Subject subject = null;
        Exception exception = null;
        try {
            subject = org.apache.shiro.SecurityUtils.getSubject();
            subject.login(token);
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            callback.run(subject, token, exception);
        }
    }

    public static void logout() {
        Subject subject = org.apache.shiro.SecurityUtils.getSubject();
        subject.logout();
    }

    public static boolean isAuthenticated() {
        return getSubject().isAuthenticated();
    }

    public static boolean isPermitted(String permission) {
        return getSubject().isPermitted(permission);
    }

    public static boolean hasRole(String roleIdentifier) {
        return getSubject().hasRole(roleIdentifier);
    }

    public static boolean isRemembered() {
        return getSubject().isRemembered();
    }

    public static Session getSession() {
        return getSession(true);
    }

    public static Session getSession(boolean create) {
        return getSubject().getSession(create);
    }

    public static Subject getSubject() {
        return org.apache.shiro.SecurityUtils.getSubject();
    }

    public static SecurityManager getSecurityManager() {
        return org.apache.shiro.SecurityUtils.getSecurityManager();
    }

    // 登录后才设置的属性

    public static Long getUserId() {
        Long userId = ContextHelper.getUserId();
        return userId == null ? (Long) getSession().getAttribute(USER_ID) : userId;
    }

    public static String getUsername() {
        String username = ContextHelper.getUsername();
        return username == null ? (String) getSession().getAttribute(USER_NAME) : username;
    }

    public static String getNickname() {
        String nickname = ContextHelper.getNickname();
        return nickname == null ? (String) getSession().getAttribute(USER_NICKNAME) : nickname;
    }

    public static String getClientId() {
        String clientId = null; // ContextHelper.getClientId();
        return clientId == null ? (String) getSession().getAttribute(CLIENT_ID) : clientId;
    }

    public static String getAccessToken() {
        return (String) getSession().getAttribute(ACCESS_TOKEN);
    }

    public static UserInfo getUserInfo() {
        return new UserInfo(getUsername(), getUserId(), getNickname(), getClientId(), getAccessToken());
    }

    public static void applyToSession(User user) {
        Session session = getSession();
        session.setAttribute(USER_ID, user.getUserId());
        session.setAttribute(USER_NAME, user.getUsername());
        session.setAttribute(USER_NICKNAME, user.getNickname());
    }

    public static void applyToSession(ClientInfo clientInfo) {
        Session session = getSession();
        session.setAttribute(CLIENT_ID, clientInfo.getClientId());
    }

    public static void applyToSession(AccessToken accessToken) {
        Session session = getSession();
        session.setAttribute(CLIENT_ID, accessToken.getClientId());
        session.setAttribute(ACCESS_TOKEN, accessToken.getAccessToken());
    }

    public interface LoginCallback {

        void run(Subject subject, AuthenticationToken token, Exception exception);

    }

    public static final class UserInfo {

        public final String username;
        public final Long userId;
        public final String nickname;
        public final String clientId;
        public final String accessToken;

        private UserInfo(String username, Long userId, String nickname, String clientId, String accessToken) {
            this.username = username;
            this.userId = userId;
            this.nickname = nickname;
            this.clientId = clientId;
            this.accessToken = accessToken;
        }

        public boolean isClientRequest() {
            return clientId != null;
        }

    }

    public static final class DefaultLoginCallback implements LoginCallback {

        private final UserService userService;
        private final AccessTokenService accessTokenService;

        public DefaultLoginCallback(UserService userService, AccessTokenService accessTokenService) {
            this.userService = userService;
            this.accessTokenService = accessTokenService;
        }

        @Override
        public void run(Subject subject, AuthenticationToken token, Exception exception) {
            if (exception == null) {
                User user = userService.findByUsername((String) token.getPrincipal());
                applyToSession(user);
                if (token instanceof OAuthAccessToken) {
                    AccessToken accessToken = accessTokenService.findOne(((OAuthAccessToken) token).getAccessToken());
                    SecurityUtils.applyToSession(accessToken);
                }
            }
        }

    }

}
