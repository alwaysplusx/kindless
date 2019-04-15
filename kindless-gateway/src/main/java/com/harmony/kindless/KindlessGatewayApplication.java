package com.harmony.kindless;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author wuxii
 */
@EnableDiscoveryClient
@SpringBootApplication
public class KindlessGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(KindlessGatewayApplication.class, args);
    }

}