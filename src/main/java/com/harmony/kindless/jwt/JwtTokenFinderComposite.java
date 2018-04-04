package com.harmony.kindless.jwt;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuxii@foxmail.com
 */
public class JwtTokenFinderComposite implements JwtTokenFinder {

    private List<JwtTokenFinder> jwtTokenFinders = new ArrayList<>();

    public JwtTokenFinderComposite() {
    }

    public JwtTokenFinderComposite(List<JwtTokenFinder> jwtTokenFinders) {
        this.jwtTokenFinders = jwtTokenFinders;
    }

    @Override
    public JwtToken find(HttpServletRequest request) {
        JwtToken jwtToken = null;
        for (JwtTokenFinder finder : jwtTokenFinders) {
            jwtToken = finder.find(request);
            if (jwtToken != null) {
                break;
            }
        }
        return jwtToken;
    }

    public void addJwtTokenFinder(JwtTokenFinder finder) {
        this.jwtTokenFinders.add(finder);
    }

    public List<JwtTokenFinder> getJwtTokenFinders() {
        return jwtTokenFinders;
    }

    public void setJwtTokenFinders(List<JwtTokenFinder> jwtTokenFinders) {
        this.jwtTokenFinders = jwtTokenFinders;
    }

}
