package com.kindless.client.feign.user;

import com.kindless.client.feign.ServiceNames;
import com.kindless.domain.user.User;
import com.kindless.dto.user.FindOrCreateUserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wuxin
 */
@FeignClient(ServiceNames.USER)
public interface UserFeignClient {

    String PATH = "/provider";

    @GetMapping(PATH + "/u/{username}")
    User findByUsername(@PathVariable("username") String username);

    @PostMapping("/findOrCreateUser")
    User findOrCreateUserByAccount(@RequestBody FindOrCreateUserRequest request);

}
