package com.kindless.core.web.filter;

import com.kindless.core.auditor.Auditor;
import com.kindless.core.auditor.WebAuditorExtractor;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wuxin
 */
public class AuditorHttpFilter extends HttpFilter {

    private WebAuditorExtractor auditorExtractor;

    public AuditorHttpFilter() {
    }

    public AuditorHttpFilter(WebAuditorExtractor auditorExtractor) {
        this.auditorExtractor = auditorExtractor;
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        Auditor exists = Auditor.getCurrent();
        try {
            Auditor current = auditorExtractor.extract(req);
            Auditor.setCurrent(current);
            super.doFilter(req, res, chain);
        } finally {
            Auditor.setCurrent(exists);
        }
    }

    public void setAuditorExtractor(WebAuditorExtractor auditorExtractor) {
        this.auditorExtractor = auditorExtractor;
    }

}
