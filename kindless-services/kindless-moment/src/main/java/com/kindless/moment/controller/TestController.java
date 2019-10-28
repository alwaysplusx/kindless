package com.kindless.moment.controller;

import com.kindless.apis.client.UserClient;
import com.kindless.apis.dto.UserDto;
import com.harmony.umbrella.web.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuxii
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping("/test")
public class TestController {

    @Value("${foo.name:default}")
    private String name;

    @Autowired
    private UserClient userClient;

    @GetMapping("/user")
    public Response<UserDto> index(String username) {
        return userClient.getUser(username);
    }

    @GetMapping("/config")
    public Response<String> config() {
        return Response.ok(this + ", " + name);
    }

}
