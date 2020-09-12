package com.kindless.gateway.filter.introspector;

import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author wuxin
 */
public interface BodyIntrospector extends RewriteFunction<String, String> {

    @Override
    Mono<String> apply(ServerWebExchange exchange, String body);

}

