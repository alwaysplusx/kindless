package com.kindless.apis.client;

import com.harmony.umbrella.web.Response;
import com.kindless.apis.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author wuxii
 */
@FeignClient(name = "kindless-user", path = "/user")
public interface UserClient {

    @GetMapping("/u/{username}")
    Response<UserDto> getUser(@PathVariable("username") String username);

    @GetMapping("/id/{userId}")
    Response<UserDto> getUserById(@PathVariable("userId") Long userId);

}
