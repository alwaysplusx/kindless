package com.kindless.core.auditor.jwt.reactive;

import com.kindless.core.WebRequestException;
import com.kindless.core.auditor.jwt.AbstractWebAuditorExtractor;
import com.kindless.core.auditor.reactive.WebAuditorExtractor;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;

/**
 * @author wuxin
 */
public class JwtWebAuditorExtractor extends AbstractWebAuditorExtractor<ServerHttpRequest> implements WebAuditorExtractor {

    @Override
    protected String extractAuthorizationToken(ServerHttpRequest request) {
        List<String> tokens = request.getHeaders().getOrEmpty(this.header);
        if (tokens.size() != 1) {
            throw new WebRequestException("illegal authorization token");
        }
        return tokens.get(0);
    }

}
