package com.kindless.todo;

import com.kindless.config.data.JpaConfig;
import com.kindless.config.lock.LockConfig;
import com.kindless.config.web.WebAdviceConfig;
import com.kindless.config.web.WebConfig;
import com.kindless.domain.todo.Todo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@EnableWebSocket
@EnableJpaRepositories
@Import({JpaConfig.class, WebConfig.class, WebAdviceConfig.class, LockConfig.class})
@EntityScan(basePackageClasses = Todo.class)
@SpringBootApplication
public class TodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }

}
