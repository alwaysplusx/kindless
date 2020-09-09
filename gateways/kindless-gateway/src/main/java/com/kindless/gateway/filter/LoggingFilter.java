package com.kindless.gateway.filter;

import com.kindless.gateway.extract.HttpInfoExtractor;
import com.kindless.gateway.extract.RequestHttpInfo;
import com.kindless.gateway.extract.ResponseHttpInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author wuxin
 */
@Slf4j
public class LoggingFilter implements GlobalFilter, Ordered {

    public static final String X_REQUEST_ID = "X-Request-Id";

    private HttpInfoExtractor httpInfoExtractor;

    public LoggingFilter() {
    }

    public LoggingFilter(HttpInfoExtractor httpInfoExtractor) {
        this.httpInfoExtractor = httpInfoExtractor;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestId = UUID.randomUUID().toString();
        ServerHttpRequest mutatedRequest = exchange
                .getRequest()
                .mutate()
                .header(X_REQUEST_ID, requestId)
                .build();
        // add response request id
        exchange.getResponse().getHeaders().add(X_REQUEST_ID, requestId);
        ServerWebExchange mutatedExchange = exchange
                .mutate()
                .request(mutatedRequest)
                .build();
        handleInbound(requestId, mutatedExchange);
        return chain
                .filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    handleOutbound(requestId, mutatedExchange);
                }));
    }

    private void handleInbound(String requestId, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        RequestHttpInfo httpInfo = httpInfoExtractor.extractRequestHttpInfo(request);
        log.info("{} request http info: \n{}", requestId, httpInfo);
    }

    private void handleOutbound(String requestId, ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        ResponseHttpInfo httpInfo = httpInfoExtractor.extractResponseHttpInfo(response);
        log.info("{} response http info: \n{}", requestId, httpInfo);
    }

    @Override
    public int getOrder() {
        return -1;
    }

    public void setHttpInfoExtractor(HttpInfoExtractor httpInfoExtractor) {
        this.httpInfoExtractor = httpInfoExtractor;
    }

}
