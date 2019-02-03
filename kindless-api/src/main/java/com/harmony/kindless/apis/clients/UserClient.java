package com.harmony.kindless.apis.clients;

import com.harmony.kindless.apis.clients.fallback.UserClientFallback;
import com.harmony.kindless.apis.support.RestUserDetails;
import com.harmony.umbrella.web.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wuxii
 */
@FeignClient(name = Client.KINDLESS_CORE, fallback = UserClientFallback.class)
public interface UserClient extends Client {

    @GetMapping("/security/user_details")
    Response<RestUserDetails> getRestUserDetails(@RequestParam("token") String token, @RequestParam("schema") String schema);

}
