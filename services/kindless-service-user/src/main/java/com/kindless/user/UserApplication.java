package com.kindless.user;

import com.kindless.config.data.JpaConfig;
import com.kindless.config.web.WebAdviceConfig;
import com.kindless.config.web.WebConfig;
import com.kindless.domain.user.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author wuxii
 */
@SpringBootApplication
@EnableJpaRepositories
@Import({JpaConfig.class, WebConfig.class, WebAdviceConfig.class})
@EntityScan(basePackageClasses = User.class)
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
