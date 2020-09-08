package com.kindless.gateway.filter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.AbstractServerHttpRequest;
import org.springframework.http.server.reactive.AbstractServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author wuxin
 */
@Slf4j
public class LoggingFilter implements GlobalFilter, Ordered {

    private static final String X_REQUEST_ID = "X-Request-Id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest mutatedRequest = exchange
                .getRequest()
                .mutate()
                .header(X_REQUEST_ID, UUID.randomUUID().toString())
                .build();
        ServerWebExchange mutatedExchange = exchange
                .mutate()
                .request(mutatedRequest)
                .build();
        handleInbound(mutatedExchange);
        return chain
                .filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    handleOutbound(mutatedExchange);
                }));
    }

    private void handleInbound(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        RequestHttpInfo httpInfo = getRequestHttpInfo(request);
        log.info("request http info: \n{}", httpInfo);
    }

    private void handleOutbound(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        ResponseHttpInfo httpInfo = getResponseHttpInfo(response);
        log.info("response http info: \n{}", httpInfo);
    }

    private ResponseHttpInfo getResponseHttpInfo(ServerHttpResponse response) {
        if (response instanceof AbstractServerHttpResponse) {
            Object nativeResponse = ((AbstractServerHttpResponse) response).getNativeResponse();
            if (nativeResponse instanceof HttpServerResponse) {
                return getResponseHttpInfoFromHttpServerResponse((HttpServerResponse) nativeResponse);
            }
        }
        return null;
    }

    private RequestHttpInfo getRequestHttpInfo(ServerHttpRequest request) {
        if (request instanceof AbstractServerHttpRequest) {
            Object nativeRequest = ((AbstractServerHttpRequest) request).getNativeRequest();
            if (nativeRequest instanceof HttpServerRequest) {
                return getRequestHttpInfoFromHttpServerRequest((HttpServerRequest) nativeRequest);
            }
            return null;
        }
        return null;
    }

    private ResponseHttpInfo getResponseHttpInfoFromHttpServerResponse(HttpServerResponse nativeResponse) {
        return new ResponseHttpInfo()
                .setVersion(nativeResponse.version().text())
                .setStatusCode(nativeResponse.status().codeAsText().toString())
                .setReason(nativeResponse.status().reasonPhrase())
                .setHttpHeaders(nativeResponse.responseHeaders());
    }

    public RequestHttpInfo getRequestHttpInfoFromHttpServerRequest(HttpServerRequest request) {
        return new RequestHttpInfo()
                .setPath(request.path())
                .setMethod(request.method().name())
                .setVersion(request.version().text())
                .setHttpHeaders(request.requestHeaders());
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private static class HttpInfo<T> {

        String version;
        Map<String, String> headers = new LinkedHashMap<>();

        public void addHeader(String name, String value) {
            headers.put(name, value);
        }

        public T setHttpHeaders(io.netty.handler.codec.http.HttpHeaders headers) {
            Iterator<Map.Entry<String, String>> iterator = headers.iteratorAsString();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                addHeader(entry.getKey(), entry.getValue());
            }
            return (T) this;
        }

        public T setVersion(String version) {
            this.version = version;
            return (T) this;
        }

        protected void appendHeaders(StringBuilder o) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                o.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
        }

    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @Accessors(chain = true)
    private static class RequestHttpInfo extends HttpInfo<RequestHttpInfo> {

        String method;
        String path;

        public void addHeader(String name, String value) {
            headers.put(name, value);
        }

        public RequestHttpInfo setHttpHeaders(io.netty.handler.codec.http.HttpHeaders headers) {
            Iterator<Map.Entry<String, String>> iterator = headers.iteratorAsString();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                addHeader(entry.getKey(), entry.getValue());
            }
            return this;
        }

        @Override
        public String toString() {
            StringBuilder o = new StringBuilder();
            o.append(method).append(" ")
                    .append(!path.startsWith("/") ? "/" : "").append(path)
                    .append(" ").append(version).append("\n");
            this.appendHeaders(o);
            return o.toString();
        }

    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    private static class ResponseHttpInfo extends HttpInfo<ResponseHttpInfo> {

        String statusCode;

        String reason;

        @Override
        public String toString() {
            StringBuilder o = new StringBuilder();
            o.append(version).append(" ")
                    .append(statusCode).append(" ")
                    .append(reason).append("\n");
            this.appendHeaders(o);
            return o.toString();
        }

    }

}
