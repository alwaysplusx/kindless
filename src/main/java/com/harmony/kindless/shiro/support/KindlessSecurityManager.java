package com.harmony.kindless.shiro.support;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.subject.WebSubjectContext;

import com.harmony.kindless.jwt.JwtToken;
import com.harmony.kindless.jwt.JwtTokenFinder;
import com.harmony.kindless.jwt.JwtTokenService;
import com.harmony.kindless.jwt.support.HttpHeaderTokenFinder;

/**
 * @author wuxii@foxmail.com
 */
public class KindlessSecurityManager extends DefaultWebSecurityManager {

    private JwtTokenService jwtTokenService;
    private JwtTokenFinder jwtTokenFinder;

    public KindlessSecurityManager() {
        this.jwtTokenFinder = new HttpHeaderTokenFinder();
    }

    @Override
    protected SessionKey getSessionKey(SubjectContext context) {
        Serializable sessionId = context.getSessionId();
        if (sessionId == null && context instanceof WebSubjectContext && ((WebSubjectContext) context).getServletRequest() != null) {
            HttpServletRequest request = (HttpServletRequest) ((WebSubjectContext) context).getServletRequest();
            JwtToken jwtToken = jwtTokenFinder.find(request);
            if (jwtToken != null && !jwtToken.isExpired()) {
                sessionId = jwtTokenService.getSessionId(jwtToken);
            }
        }
        return sessionId != null ? new DefaultSessionKey(sessionId) : null;
    }

    @Override
    public boolean isHttpSessionMode() {
        return true;
    }

    public JwtTokenService getJwtTokenService() {
        return jwtTokenService;
    }

    public void setJwtTokenService(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    public JwtTokenFinder getJwtTokenFinder() {
        return jwtTokenFinder;
    }

    public void setJwtTokenFinder(JwtTokenFinder jwtTokenFinder) {
        this.jwtTokenFinder = jwtTokenFinder;
    }
}
