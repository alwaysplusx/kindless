package com.kindless.client.feign.user;

import com.kindless.client.feign.ServiceNames;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wuxin
 */
@FeignClient(ServiceNames.USER)
public interface UserFeignClient {

    String PATH = "/provider";

    @GetMapping(PATH + "/echo")
    String echo(@RequestParam("name") String name);

}
