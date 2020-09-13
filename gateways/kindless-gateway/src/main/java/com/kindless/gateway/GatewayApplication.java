package com.kindless.gateway;

import com.kindless.core.CodeResponse;
import com.kindless.core.web.error.reactive.GlobalWebErrorHandler;
import com.kindless.gateway.extract.SimpleHttpInfoExtractor;
import com.kindless.gateway.filter.LoggingFilter;
import com.kindless.gateway.web.JsonWebErrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

/**
 * @author wuxii
 */
@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public GlobalFilter loggingFilter() {
        LoggingFilter filter = new LoggingFilter();
        filter.setHttpInfoExtractor(new SimpleHttpInfoExtractor());
        filter.setBodyIntrospector((exchange, body) -> {
            exchange
                    .getRequest()
                    .getHeaders()
                    .getOrEmpty(LoggingFilter.X_TRACE_ID)
                    .stream()
                    .findFirst()
                    .ifPresent(e -> {
                        log.info("current request id: {}", e);
                    });
            log.info("body introspector: {}", body);
            return body == null ? Mono.empty() : Mono.just(body);
        });
        return filter;
    }

    @Bean
    public ErrorWebExceptionHandler webErrorHandler() {
        GlobalWebErrorHandler handler = GlobalWebErrorHandler
                .builder()
                .setDefaultErrorCode(CodeResponse.ERROR)
                .setDefaultErrorMessage("unknown_error")
                .setDefaultHttpStatus(HttpStatus.OK.value())
                .build();
        return new JsonWebErrorHandler(handler);
    }

}
