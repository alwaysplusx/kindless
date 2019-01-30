package com.harmony.kindless.moment.controller;

import com.harmony.kindless.apis.clients.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuxii
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserClient userClient;

    @GetMapping("/security")
    public Object test(String username) {
        return userClient.getUserSecurityData(username);
    }

}
