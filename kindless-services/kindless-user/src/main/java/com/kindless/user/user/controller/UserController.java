package com.kindless.user.user.controller;

import com.harmony.umbrella.context.CurrentUser;
import com.harmony.umbrella.web.Response;
import com.harmony.umbrella.web.method.annotation.BundleController;
import com.harmony.umbrella.web.method.annotation.BundleView;
import com.kindless.apis.Responses;
import com.kindless.apis.dto.UserDto;
import com.kindless.user.domain.User;
import com.kindless.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotNull;

/**
 * @author wuxii
 */
@Slf4j
@BundleController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @BundleView({"password", "userSettings", "userDetails", "new"})
    @GetMapping("/u/{username}")
    public Response<UserDto> user(@PathVariable String username) {
        if (RandomUtils.nextBoolean()) {
            throw Responses.ERROR.toException();
        }
        User user = userService.getByUsername(username);
        return Response.ok(new UserDto(user));
    }

    @BundleView({"password", "userSettings", "userDetails", "new"})
    @GetMapping("/id/{userId}")
    public Response<UserDto> user(@NotNull @PathVariable Long userId, CurrentUser user) {
        log.info("获取用户的信息: userId={}. current user:{}", userId, user);
        return userService.findById(userId)
                .map(UserDto::new)
                .map(Response::ok)
                .orElseGet(Responses::notExists);
    }

}
