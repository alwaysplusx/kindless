package com.kindless.client.feign.user;

import com.kindless.client.feign.ServiceNames;
import com.kindless.domain.user.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author wuxin
 */
@FeignClient(ServiceNames.USER)
public interface UserFeignClient {

    String PATH = "/provider";

    @GetMapping(PATH + "/u/{username}")
    User findByUsername(@PathVariable("username") String username);

}
