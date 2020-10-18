package com.kindless.todo.controller;

import com.kindless.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Slf4j
@Controller
public class TodoWsController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final TodoService todoService;

    @MessageMapping("/ping")
    @SendToUser("/queue/connected")
    public String ping(String msg) {
        log.info("add user ping todo list: {}", msg);
        return msg + " Pong!";
    }

    @MessageMapping("/unping")
    @SendTo("/topic/disconnected")
    public String unping(String msg) {
        log.info("remove user ping todo list: {}", msg);
        return msg + " Unpong!";
    }

    @MessageMapping("/message")
    public void message(String msg) {
        log.info("handle message to: {}", msg);
        simpMessagingTemplate.convertAndSendToUser(msg, "/message", "current scene @" + System.currentTimeMillis());
    }

}
