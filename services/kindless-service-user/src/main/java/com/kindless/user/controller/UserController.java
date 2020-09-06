package com.kindless.user.controller;

import com.kindless.core.WebResponse;
import com.kindless.core.dto.IdDto;
import com.kindless.domain.user.User;
import com.kindless.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author wuxii
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/u/{username}")
    public WebResponse<User> user(@PathVariable String username) {
        User user = userService.getByUsername(username);
        return WebResponse.ok(user);
    }

    @PutMapping("/")
    public WebResponse<IdDto> putUser(@RequestBody User user) {
        User persistedUser = userService.save(user);
        return WebResponse.ok(IdDto.of(persistedUser));
    }

    @GetMapping("/list")
    public WebResponse<List<User>> users() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<User> users = userService.findAll(sort);
        return WebResponse.ok(users);
    }

}
