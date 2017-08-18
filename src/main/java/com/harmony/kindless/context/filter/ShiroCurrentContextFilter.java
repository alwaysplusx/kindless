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
        return new ShiroCurrentContext((HttpServletRequest) request, (HttpServletResponse) response);
    }

    private static class ShiroCurrentContext extends DefaultCurrentContext {

        private static final long serialVersionUID = -2681738645946864322L;

        private Subject subject;

        public ShiroCurrentContext(HttpServletRequest request, HttpServletResponse response) {
            super(request, response);
        }

        @Override
        public boolean isAuthenticated() {
            Subject subject = getSubject();
            return subject != null && subject.isAuthenticated();
        }

        @Override
        public <T> T getSessionAttribute(String name) {
            Object val = super.getSessionAttribute(name);
            return val == null ? getShiroSessionAttribute(name) : (T) val;
        }

        public <T> T getShiroSessionAttribute(String name) {
            Session session = getSubject().getSession();
            return (T) session.getAttribute(name);
        }

        protected Subject getSubject() {
            if (subject == null) {
                try {
                    subject = SecurityUtils.getSubject();
                } catch (Exception e) {
                    // ignore
                }
            }
            return subject;
        }

    }
}
