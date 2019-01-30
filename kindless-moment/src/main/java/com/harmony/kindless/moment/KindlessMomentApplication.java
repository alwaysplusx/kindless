package com.harmony.kindless.moment;

import com.harmony.kindless.apis.clients.UserClient;
import com.harmony.kindless.apis.support.RestJwtUserDetailsService;
import com.harmony.kindless.moment.web.CurrentUserHandlerMethodArgumentResolver;
import com.harmony.umbrella.data.repository.support.QueryableRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author wuxii
 */
@EnableFeignClients("com.harmony.kindless.apis.clients")
@SpringBootApplication
@EntityScan("com.harmony.kindless.apis.domain.moment")
@EnableJpaRepositories(repositoryFactoryBeanClass = QueryableRepositoryFactoryBean.class)
public class KindlessMomentApplication {

    public static void main(String[] args) {
        SpringApplication.run(KindlessMomentApplication.class, args);
    }

    @Bean
    public RestJwtUserDetailsService jwtUserDetailsService(UserClient userClient) {
        return new RestJwtUserDetailsService(userClient);
    }

    @Bean
    public WebMvcConfigurer localWebMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
                resolvers.add(new CurrentUserHandlerMethodArgumentResolver());
            }
        };
    }

}
