package com.harmony.kindless.shiro.listener;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationListener;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.PrincipalCollection;

import com.harmony.kindless.core.domain.ClientInfo;
import com.harmony.kindless.core.domain.User;

/**
 * @author wuxii@foxmail.com
 */
public class SessionAuthenticationListener implements AuthenticationListener {

    public SessionAuthenticationListener() {
    }

    @Override
    public void onSuccess(AuthenticationToken token, AuthenticationInfo info) {
        PrincipalCollection principals = info.getPrincipals();
        Object primaryPrincipal = principals.getPrimaryPrincipal();
        if (primaryPrincipal instanceof User) {

        } else if (primaryPrincipal instanceof ClientInfo) {

        }
        // User user = securityService.findUser((String) token.getPrincipal());
        // Map<String, Object> props = new HashMap<>();
        // Collection<ThridpartPrincipal> tpps = info.getPrincipals().byType(ThridpartPrincipal.class);
        // if (tpps != null && !tpps.isEmpty()) {
        // ThridpartPrincipal tpp = tpps.iterator().next();
        // props.put("client", tpp.getClient());
        // props.put("username", tpp.getUsername());
        // props.put("scope", tpp.getScope());
        // }
        // UserPrincipal up = new CurrentContext.UserPrincipal(user.getUserId(), user.getUsername(), user.getNickname(),
        // props);
        // SecurityUtils.applyUserPrincipal(up);
    }

    @Override
    public void onLogout(PrincipalCollection principals) {
    }

    @Override
    public void onFailure(AuthenticationToken token, AuthenticationException ae) {
    }

}
