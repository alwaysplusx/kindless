package com.kindless.todo.config;

import com.kindless.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
@Configuration
public class ScheduleConfig {

    private final SimpMessagingTemplate messagingTemplate;

    private final TodoService todoService;

    @Scheduled(fixedDelay = 1000)
    public void sendMessage() {

    }

}
