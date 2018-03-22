package com.harmony.kindless.shiro.support;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.subject.WebSubjectContext;

import com.harmony.kindless.core.service.SecurityService;
import com.harmony.kindless.shiro.JwtToken;
import com.harmony.kindless.util.SecurityUtils;

/**
 * @author wuxii@foxmail.com
 */
public class KindlessSecurityManager extends DefaultWebSecurityManager implements SecurityServiceAware {

    private String tokenName;
    private SecurityService securityService;

    public KindlessSecurityManager() {
    }

    public KindlessSecurityManager(String tokenName) {
        this.setTokenName(tokenName);
    }

    @Override
    protected SessionKey getSessionKey(SubjectContext context) {
        Serializable sessionId = context.getSessionId();
        if (sessionId == null && context instanceof WebSubjectContext) {
            JwtToken jwtToken = getRequestJwtToken((WebSubjectContext) context);
            if (jwtToken != null && !jwtToken.isExpired()) {
                sessionId = securityService.getSessionId(jwtToken);
            }
        }
        return sessionId != null ? new DefaultSessionKey(sessionId) : null;
    }

    protected JwtToken getRequestJwtToken(WebSubjectContext context) {
        HttpServletRequest request = (HttpServletRequest) context.getServletRequest();
        return request != null ? SecurityUtils.getRequestToken(request, tokenName) : null;
    }

    @Override
    public boolean isHttpSessionMode() {
        return true;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public SecurityService getSecurityService() {
        return securityService;
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

}
