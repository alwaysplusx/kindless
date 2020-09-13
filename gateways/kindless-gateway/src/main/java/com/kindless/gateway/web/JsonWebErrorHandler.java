package com.kindless.gateway.web;

import com.kindless.core.web.WebErrorResponse;
import com.kindless.core.web.reactive.WebErrorHandler;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * @author wuxin
 */
public class JsonWebErrorHandler implements ErrorWebExceptionHandler {

    private WebErrorHandler errorHandler;
    private List<HttpMessageReader<?>> messageReaders = HandlerStrategies.withDefaults().messageReaders();
    private List<HttpMessageWriter<?>> messageWriters = HandlerStrategies.withDefaults().messageWriters();

    public JsonWebErrorHandler() {
    }

    public JsonWebErrorHandler(WebErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable error) {
        ServerRequest request = ServerRequest.create(exchange, messageReaders);
        WebErrorResponse errorResponse = errorHandler.handle(error, exchange);
        return RouterFunctions.route(RequestPredicates.all(), serverRequest -> this.renderErrorResponse(serverRequest, errorResponse))
                .route(request)
                .switchIfEmpty(Mono.error(error))
                .flatMap((handler) -> handler.handle(request))
                .flatMap((response) -> write(exchange, response));
    }

    private Mono<? extends Void> write(ServerWebExchange exchange, ServerResponse response) {
        exchange.getResponse().getHeaders().setContentType(response.headers().getContentType());
        return response.writeTo(exchange, new ServerResponse.Context() {
            @Override
            public List<HttpMessageWriter<?>> messageWriters() {
                return messageWriters;
            }

            @Override
            public List<ViewResolver> viewResolvers() {
                return Collections.emptyList();
            }
        });
    }

    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request, WebErrorResponse errorResponse) {
        return ServerResponse
                .status(errorResponse.getHttpStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(errorResponse.getBody()));
    }

    public void setErrorHandler(WebErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public void setMessageReaders(List<HttpMessageReader<?>> messageReaders) {
        this.messageReaders = messageReaders;
    }

    public void setMessageWriters(List<HttpMessageWriter<?>> messageWriters) {
        this.messageWriters = messageWriters;
    }

}
