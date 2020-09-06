package com.kindless.moment;

import com.kindless.domain.moment.Moment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author wuxii
 */
@SpringBootApplication
@EnableJpaRepositories
@EntityScan(basePackageClasses = Moment.class)
public class MomentApplication {

    public static void main(String[] args) {
        SpringApplication.run(MomentApplication.class, args);
    }

}
