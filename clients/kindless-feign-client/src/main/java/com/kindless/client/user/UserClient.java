package com.kindless.client.user;

import com.kindless.client.ServiceNames;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wuxin
 */
@FeignClient(ServiceNames.USER)
public interface UserClient {

    String PATH = "/provider";

    @GetMapping(PATH + "/echo")
    String echo(@RequestParam("name") String name);

}
