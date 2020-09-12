package com.kindless.user;

import com.kindless.config.data.JpaConfig;
import com.kindless.config.web.WebAdviceConfig;
import com.kindless.config.web.WebConfig;
import com.kindless.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wuxii
 */
@Slf4j
@SpringBootApplication
@EnableJpaRepositories
@Import({JpaConfig.class, WebConfig.class, WebAdviceConfig.class})
@EntityScan(basePackageClasses = User.class)
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner() {
        return args -> {
            AtomicInteger count = new AtomicInteger();
            Executors.newScheduledThreadPool(1)
                    .scheduleAtFixedRate(() -> {
                        log.info("current number: {}", count.getAndIncrement());
                    }, 0, 1, TimeUnit.SECONDS);
        };
    }

}
