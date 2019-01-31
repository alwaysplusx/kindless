package com.harmony.kindless.core;

import com.harmony.umbrella.data.repository.support.QueryableRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author wuxii
 */
@EnableFeignClients("com.harmony.kindless.apis.clients")
@SpringBootApplication
@EntityScan("com.harmony.kindless.apis.domain.core")
@EnableJpaRepositories(repositoryFactoryBeanClass = QueryableRepositoryFactoryBean.class)
public class KindlessCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(KindlessCoreApplication.class, args);
    }

}
