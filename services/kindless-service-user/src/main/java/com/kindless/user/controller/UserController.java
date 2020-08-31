package com.kindless.user.controller;

import com.kindless.core.WebResponse;
import com.kindless.user.domain.User;
import com.kindless.user.dto.UserDto;
import com.kindless.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author wuxii
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/u/{username}")
    public WebResponse<UserDto> user(@PathVariable String username) {
        User user = userService.getByUsername(username);
        return WebResponse.ok(new UserDto(user));
    }

}
