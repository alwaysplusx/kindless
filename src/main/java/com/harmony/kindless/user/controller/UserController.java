package com.harmony.kindless.user.controller;

import com.harmony.kindless.user.service.UserService;
import com.harmony.umbrella.web.method.annotation.BundleController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wuxii
 */
@BundleController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public Object test(@RequestParam String username) {
        return userService.getOrCreate(username);
    }

}
