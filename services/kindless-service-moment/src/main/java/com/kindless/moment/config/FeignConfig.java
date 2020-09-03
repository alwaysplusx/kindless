package com.kindless.moment.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author wuxin
 */
@Configurable
@EnableFeignClients({"com.kindless.client.user"})
public class FeignConfig {
}
