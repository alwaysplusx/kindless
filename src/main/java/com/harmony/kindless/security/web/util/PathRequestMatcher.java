package com.harmony.kindless.security.web.util;

import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author wuxii
 */
public class PathRequestMatcher implements RequestMatcher {

    protected final PathMatcher pathMatcher;
    protected final Set<String> includes = new LinkedHashSet<>();
    protected final Set<String> excludes = new LinkedHashSet<>();

    public PathRequestMatcher() {
        this(new AntPathMatcher("/"));
    }

    public PathRequestMatcher(Collection<String> includes, Collection<String> excludes) {
        this.pathMatcher = new AntPathMatcher();
        this.includes.addAll(includes);
        this.excludes.addAll(excludes);
    }

    public PathRequestMatcher(PathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        String path = getRequestPath(request);
        return includes.isEmpty()
                ? excludes.isEmpty() || !isMatched(path, excludes)
                : isMatched(path, includes) && (excludes.isEmpty() || !isMatched(path, excludes));
    }

    protected boolean isMatched(String path, Set<String> patterns) {
        for (String s : patterns) {
            if (pathMatcher.match(s, path)) {
                return true;
            }
        }
        return false;
    }

    protected String getRequestPath(HttpServletRequest request) {
        return request.getRequestURI();
    }

    public Set<String> getIncludes() {
        return includes;
    }

    public Set<String> getExcludes() {
        return excludes;
    }

}
