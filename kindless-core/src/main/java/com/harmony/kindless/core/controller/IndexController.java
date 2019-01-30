package com.harmony.kindless.core.controller;

import com.harmony.kindless.apis.dto.UserSecurityData;
import com.harmony.kindless.core.service.UserService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuxii
 */
@RestController
public class IndexController {

    @Autowired
    private UserService userService;

    @GetMapping("/security/user")
    public UserSecurityData userDetails(Long userId, String username) {
        if (RandomUtils.nextBoolean()) {
            throw new RuntimeException("custom exception");
        }
        return userService.getUserSecurityData(userId, username);
    }

}
