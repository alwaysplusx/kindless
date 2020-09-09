package com.kindless.gateway;

import com.kindless.gateway.extract.SimpleHttpInfoExtractor;
import com.kindless.gateway.filter.LoggingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;

/**
 * @author wuxii
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public GlobalFilter inboundOutboundGlobalFilter() {
        return new LoggingFilter(new SimpleHttpInfoExtractor());
    }

}
