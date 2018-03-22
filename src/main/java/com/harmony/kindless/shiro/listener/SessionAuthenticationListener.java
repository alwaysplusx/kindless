package com.harmony.kindless.shiro.listener;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationListener;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.PrincipalCollection;

import com.harmony.kindless.core.domain.User;
import com.harmony.kindless.core.service.SecurityService;
import com.harmony.kindless.shiro.JwtToken.ThridpartPrincipal;
import com.harmony.kindless.util.SecurityUtils;
import com.harmony.umbrella.context.CurrentContext;
import com.harmony.umbrella.context.CurrentContext.UserPrincipal;

/**
 * @author wuxii@foxmail.com
 */
public class SessionAuthenticationListener implements AuthenticationListener {

    private SecurityService securityService;

    public SessionAuthenticationListener() {
    }

    public SessionAuthenticationListener(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void onSuccess(AuthenticationToken token, AuthenticationInfo info) {
        User user = securityService.findUser((String) token.getPrincipal());
        Map<String, Object> props = new HashMap<>();
        Collection<ThridpartPrincipal> tpps = info.getPrincipals().byType(ThridpartPrincipal.class);
        if (tpps != null && !tpps.isEmpty()) {
            ThridpartPrincipal tpp = tpps.iterator().next();
            props.put("client", tpp.getClient());
            props.put("username", tpp.getUsername());
            props.put("scope", tpp.getScope());
        }
        UserPrincipal up = new CurrentContext.UserPrincipal(user.getUserId(), user.getUsername(), user.getNickname(), props);
        SecurityUtils.applyUserPrincipal(up);
    }

    @Override
    public void onLogout(PrincipalCollection principals) {
        SecurityUtils.clearUserPrincipal();
    }

    @Override
    public void onFailure(AuthenticationToken token, AuthenticationException ae) {
    }

    public SecurityService getSecurityService() {
        return securityService;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

}
