package com.harmony.kindless.context.filter;

import java.util.Set;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.umbrella.context.AbstractCurrentContextFilter;
import com.harmony.umbrella.context.ContextHelper;
import com.harmony.umbrella.context.CurrentContext;
import com.harmony.umbrella.util.PatternResourceFilter;
import com.harmony.umbrella.util.StringUtils;

/**
 * @author wuxii@foxmail.com
 */
public class ShiroCurrentContextFilter extends AbstractCurrentContextFilter {

    private PatternResourceFilter<String> resourceFilter;
    private String hostHeader;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (resourceFilter == null) {
            this.resourceFilter = new PatternResourceFilter<>();
            this.resourceFilter.addIncludes(asSet(filterConfig.getInitParameter("include")));
            this.resourceFilter.addExcludes(asSet(filterConfig.getInitParameter("exclude")));
            this.hostHeader = filterConfig.getInitParameter("host-header");
        }
    }

    @Override
    protected boolean isCurrentContextRequest(HttpServletRequest req, HttpServletResponse resp) {
        String url = ContextHelper.getRequestUrl(req);
        return (url.startsWith("/") && url.length() > 1 && resourceFilter.accept(url.substring(1))) //
                || resourceFilter.accept(url);
    }

    @Override
    protected CurrentContext createCurrentContext(HttpServletRequest request, HttpServletResponse response) {
        return new ShiroCurrentContext(request, response, hostHeader);
    }

    @Override
    public void destroy() {
    }

    private Set<String> asSet(String s) {
        return StringUtils.tokenizeToStringSet(s, ",");
    }

    public PatternResourceFilter<String> getResourceFilter() {
        return resourceFilter;
    }

    public void setResourceFilter(PatternResourceFilter<String> resourceFilter) {
        this.resourceFilter = resourceFilter;
    }

}
