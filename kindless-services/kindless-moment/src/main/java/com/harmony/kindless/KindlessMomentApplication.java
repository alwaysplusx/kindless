package com.harmony.kindless;

import com.harmony.kindless.apis.clients.UserClient;
import com.harmony.kindless.apis.support.RestUserDetailsService;
import com.harmony.umbrella.data.repository.support.QueryableRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author wuxii
 */
@SpringCloudApplication
@EnableFeignClients("com.harmony.kindless.apis.clients")
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
