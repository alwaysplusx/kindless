package com.kindless.gateway.filter;

import com.kindless.gateway.extract.HttpInfoExtractor;
import com.kindless.gateway.extract.RequestHttpInfo;
import com.kindless.gateway.extract.ResponseHttpInfo;
import com.kindless.gateway.filter.introspector.BodyIntrospector;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

/**
 * @author wuxin
 */
@Slf4j
public class LoggingFilter implements GlobalFilter, Ordered {

    public static final String X_TRACE_ID = "X-Krace-Id";

    private HttpInfoExtractor httpInfoExtractor;

    private BodyIntrospector bodyIntrospector;

    private List<HttpMessageReader<?>> messageReaders = HandlerStrategies.withDefaults().messageReaders();

    public LoggingFilter() {
    }

    public LoggingFilter(HttpInfoExtractor httpInfoExtractor) {
        this.httpInfoExtractor = httpInfoExtractor;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String traceId = UUID.randomUUID().toString();
        ServerHttpRequest mutatedRequest = exchange
                .getRequest()
                .mutate()
                .header(X_TRACE_ID, traceId)
                .build();
        exchange.getResponse().getHeaders().add(X_TRACE_ID, traceId);
        ServerWebExchange mutatedExchange = exchange
                .mutate()
                .request(mutatedRequest)
                .build();
        if (isReadableRequest(mutatedRequest)) {
            HttpHeaders headers = new HttpHeaders();
            headers.putAll(mutatedRequest.getHeaders());
            headers.remove(HttpHeaders.CONTENT_LENGTH);
            CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(mutatedExchange, headers);
            return BodyInserters
                    .fromPublisher(introspectBody(mutatedExchange), String.class)
                    .insert(outputMessage, new BodyInserterContext())
                    .then(Mono.defer(() -> {
                        ServerWebExchange cachedMutatedExchange = mutatedExchange
                                .mutate()
                                .request(decorateRequest(mutatedExchange, headers, outputMessage))
                                .build();
                        return doFilter(traceId, cachedMutatedExchange, chain);
                    }))
                    .onErrorResume(Mono::error);
        }
        return doFilter(traceId, mutatedExchange, chain);
    }

    private Mono<Void> doFilter(String requestId, ServerWebExchange exchange, GatewayFilterChain chain) {
        handleInbound(requestId, exchange);
        return chain
                .filter(exchange.mutate().response(decorateResponse(exchange)).build())
                .then(Mono.fromRunnable(() -> handleOutbound(requestId, exchange)));
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

    private Mono<String> introspectBody(ServerWebExchange exchange) {
        return ServerRequest
                .create(exchange, messageReaders)
                .bodyToMono(String.class)
                .flatMap(s -> bodyIntrospector.apply(exchange, s))
                .switchIfEmpty(Mono.defer(() -> bodyIntrospector.apply(exchange, null)));
    }

    @Override
    public int getOrder() {
        return -1;
    }

    public void setHttpInfoExtractor(HttpInfoExtractor httpInfoExtractor) {
        this.httpInfoExtractor = httpInfoExtractor;
    }

    protected boolean isReadableContentType(MediaType contentType) {
        ResolvableType resolvableType = ResolvableType.forRawClass(String.class);
        return messageReaders.stream().anyMatch(e -> e.canRead(resolvableType, contentType));
    }

    protected ServerHttpRequestDecorator decorateRequest(ServerWebExchange exchange, HttpHeaders headers, CachedBodyOutputMessage outputMessage) {
        return new CachedServerHttpRequest(exchange.getRequest(), headers, outputMessage);
    }

    protected ServerHttpResponseDecorator decorateResponse(ServerWebExchange exchange) {
        return new CachedServerHttpResponse(exchange);
    }

    private boolean isReadableRequest(ServerHttpRequest request) {
        MediaType contentType = request.getHeaders().getContentType();
        return isReadableContentType(contentType);
    }

    private boolean isReadableResponse(ServerHttpResponse response) {
        MediaType contentType = response.getHeaders().getContentType();
        return isReadableContentType(contentType);
    }

    public void setBodyIntrospector(BodyIntrospector bodyIntrospector) {
        this.bodyIntrospector = bodyIntrospector;
    }

    public void setMessageReaders(List<HttpMessageReader<?>> messageReaders) {
        this.messageReaders = messageReaders;
    }

    private class CachedServerHttpRequest extends ServerHttpRequestDecorator {

        private HttpHeaders headers;
        private CachedBodyOutputMessage outputMessage;

        public CachedServerHttpRequest(ServerHttpRequest delegate, HttpHeaders headers, CachedBodyOutputMessage outputMessage) {
            super(delegate);
            this.headers = headers;
            this.outputMessage = outputMessage;
        }

        @Override
        public HttpHeaders getHeaders() {
            long contentLength = headers.getContentLength();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.putAll(headers);
            if (contentLength > 0) {
                httpHeaders.setContentLength(contentLength);
            } else {
                httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
            }
            return httpHeaders;
        }

        @Override
        public Flux<DataBuffer> getBody() {
            return outputMessage.getBody();
        }

    }

    private class CachedServerHttpResponse extends ServerHttpResponseDecorator {

        private ServerWebExchange exchange;

        public CachedServerHttpResponse(ServerWebExchange exchange) {
            super(exchange.getResponse());
            this.exchange = exchange;
        }

        @Override
        public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
            String originResponseContentType = exchange.getAttribute(ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, originResponseContentType);
            ClientResponse clientResponse = ClientResponse
                    .create(exchange.getResponse().getStatusCode(), messageReaders)
                    .headers(e -> e.putAll(headers))
                    .body(Flux.from(body))
                    .build();
            Mono<String> modifiedBody = clientResponse.bodyToMono(String.class)
                    .flatMap(s -> bodyIntrospector.apply(exchange, s))
                    .switchIfEmpty(Mono.defer(() -> bodyIntrospector.apply(exchange, null)));
            CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, exchange.getResponse().getHeaders());
            return BodyInserters
                    .fromPublisher(modifiedBody, String.class)
                    .insert(outputMessage, new BodyInserterContext())
                    .then(Mono.defer(() -> {
                        Mono<DataBuffer> response = DataBufferUtils.join(outputMessage.getBody());
                        return exchange.getResponse().writeWith(response);
                    }));
        }

        @Override
        public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
            return writeWith(Flux.from(body).flatMapSequential(p -> p));
        }

    }

}
