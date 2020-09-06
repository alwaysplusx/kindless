package com.kindless.moment.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuxin
 */
@Configuration
@EnableFeignClients("com.kindless.client.feign.user")
public class FeignConfig {
}
