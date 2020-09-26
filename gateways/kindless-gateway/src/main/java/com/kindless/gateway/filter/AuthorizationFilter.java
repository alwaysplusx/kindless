package com.kindless.gateway.filter;

import com.kindless.core.auditor.Auditor;
import com.kindless.core.auditor.reactive.WebAuditorExtractor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.RequestPath;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author wuxin
 */
public class AuthorizationFilter implements GlobalFilter {

    private WebAuditorExtractor auditorExtractor;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        RequestPath path = exchange.getRequest().getPath();
        Auditor auditor = auditorExtractor.extract(exchange.getRequest());
        return null;
    }

    public void setAuditorExtractor(WebAuditorExtractor auditorExtractor) {
        this.auditorExtractor = auditorExtractor;
    }

}
