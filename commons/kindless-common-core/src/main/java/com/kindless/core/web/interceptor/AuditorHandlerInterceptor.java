package com.kindless.core.web.interceptor;

import com.kindless.core.auditor.Auditor;
import com.kindless.core.auditor.WebAuditorExtractor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wuxin
 */
public class AuditorHandlerInterceptor implements HandlerInterceptor {

    private WebAuditorExtractor auditorExtractor;

    public AuditorHandlerInterceptor() {
    }

    public AuditorHandlerInterceptor(WebAuditorExtractor auditorExtractor) {
        this.auditorExtractor = auditorExtractor;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Auditor auditor = auditorExtractor.extract(request);
        Auditor.setCurrent(auditor);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Auditor.setCurrent(null);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Auditor.setCurrent(null);
    }

    public void setAuditorExtractor(WebAuditorExtractor auditorExtractor) {
        this.auditorExtractor = auditorExtractor;
    }

}
