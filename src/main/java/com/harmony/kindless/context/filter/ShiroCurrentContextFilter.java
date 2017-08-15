package com.harmony.kindless.context.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.harmony.umbrella.context.CurrentContext;
import com.harmony.umbrella.context.CurrentContextFilter;
import com.harmony.umbrella.context.DefaultCurrentContext;

/**
 * @author wuxii@foxmail.com
 */
public class ShiroCurrentContextFilter extends CurrentContextFilter {

    @Override
    protected CurrentContext createCurrentContext(ServletRequest request, ServletResponse response) {
        ShiroCurrentContext scc = new ShiroCurrentContext((HttpServletRequest) request, (HttpServletResponse) response);
        return scc;
    }

    private static class ShiroCurrentContext extends DefaultCurrentContext {

        private static final long serialVersionUID = -2681738645946864322L;

        protected final Subject subject;

        public ShiroCurrentContext(HttpServletRequest request, HttpServletResponse response) {
            super(request, response);
            this.subject = SecurityUtils.getSubject();
        }

        @Override
        public boolean isAuthenticated() {
            return subject.isAuthenticated();
        }

        @Override
        public <T> T getSessionAttribute(String name) {
            Object val = super.getSessionAttribute(name);
            return val == null ? getShiroSessionAttribute(name) : (T) val;
        }

        public <T> T getShiroSessionAttribute(String name) {
            Session session = subject.getSession();
            return (T) session.getAttribute(name);
        }

    }
}
