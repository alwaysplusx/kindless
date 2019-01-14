package com.harmony.kindless;

import com.harmony.kindless.kafka.KafkaServiceImpl;
import com.harmony.umbrella.data.repository.support.QueryableRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author wuxii@foxmail.com
 */
@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = QueryableRepositoryFactoryBean.class)
public class KindlessApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(KindlessApplication.class, args);
    }

    @Bean
    public KafkaServiceImpl kafkaService(KafkaTemplate<String, String> kafkaTemplate) {
        return new KafkaServiceImpl(kafkaTemplate, 10);
    }

}


