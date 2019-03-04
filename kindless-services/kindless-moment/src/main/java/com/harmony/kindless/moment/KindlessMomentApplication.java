package com.harmony.kindless.moment;

import com.harmony.kindless.apis.clients.UserClient;
import com.harmony.kindless.apis.support.RestUserDetailsService;
import com.harmony.umbrella.data.repository.support.QueryableRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author wuxii
 */
@EnableHystrixDashboard
@EnableCircuitBreaker
@EnableFeignClients("com.harmony.kindless.apis.clients")
@SpringBootApplication(scanBasePackages = "com.harmony.kindless")
@EntityScan("com.harmony.kindless.apis.domain.moment")
@EnableJpaRepositories(repositoryFactoryBeanClass = QueryableRepositoryFactoryBean.class)
public class KindlessMomentApplication {

    public static void main(String[] args) {
        SpringApplication.run(KindlessMomentApplication.class, args);
    }

    @Bean
    public RestUserDetailsService jwtUserDetailsService(UserClient userClient) {
        return new RestUserDetailsService(userClient);
    }

}
