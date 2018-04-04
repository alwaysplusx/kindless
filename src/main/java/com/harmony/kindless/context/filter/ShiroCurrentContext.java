package com.harmony.kindless.context.filter;

import java.io.Serializable;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.harmony.kindless.shiro.PrimaryPrincipal;
import com.harmony.kindless.util.SecurityUtils;
import com.harmony.umbrella.context.CurrentContext;

/**
 * @author wuxii@foxmail.com
 */
public class ShiroCurrentContext implements CurrentContext, Serializable {

    private static final long serialVersionUID = -8718995377070656489L;

    private Locale locale;
    private final String hostHeader;
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    protected ShiroCurrentContext(HttpServletRequest request, HttpServletResponse response, String hostHeader) {
        this.request = request;
        this.response = response;
        this.hostHeader = hostHeader;
    }

    @Override
    public PrimaryPrincipal getPrincipals() {
        return SecurityUtils.getPrimaryPrincipal();
    }

    @Override
    public String getHost() {
        return hostHeader == null ? request.getRemoteHost() : request.getHeader(hostHeader);
    }

    @Override
    public Locale getLocale() {
        return locale == null ? request.getLocale() : locale;
    }

    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
        this.response.setLocale(locale);
    }

    @Override
    public String getCharacterEncoding() {
        return request.getCharacterEncoding();
    }

    @Override
    public <T> T getRequest(Class<T> type) {
        if (ServletRequest.class.isAssignableFrom(type)) {
            return (T) request;
        }
        if (HttpSession.class.isAssignableFrom(type)) {
            return (T) request.getSession();
        }
        if (ServletContext.class.isAssignableFrom(type)) {
            return (T) request.getServletContext();
        }
        throw new IllegalArgumentException("unsupport request type " + type);
    }

    @Override
    public <T> T getResponse(Class<T> type) {
        if (ServletResponse.class.isAssignableFrom(type)) {
            return (T) response;
        }
        throw new IllegalArgumentException("unsupport response type " + type);
    }

}
