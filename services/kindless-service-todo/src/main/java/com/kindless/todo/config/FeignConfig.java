package com.kindless.todo.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("com.kindless.client.feign.user")
public class FeignConfig {
}
