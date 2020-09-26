package com.kindless.core.auditor.jwt;

import com.kindless.core.auditor.WebAuditorExtractor;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuxin
 */
@Setter
public class JwtWebAuditorExtractor extends AbstractWebAuditorExtractor<HttpServletRequest> implements WebAuditorExtractor {

    @Override
    protected String extractAuthorizationToken(HttpServletRequest request) {
        return request.getHeader(this.header);
    }

}
