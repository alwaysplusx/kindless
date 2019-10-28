package com.kindless.apis.clients;

import com.kindless.apis.dto.UserDto;
import com.kindless.apis.support.RestUserDetails;
import com.harmony.umbrella.security.SecurityToken;
import com.harmony.umbrella.web.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wuxii
 */
@FeignClient(name = Client.KINDLESS_CORE)
public interface UserClient extends Client {

    // FIXME 反序列化的response支持

    @GetMapping("/security/user_details")
    Response<RestUserDetails> getRestUserDetails(@RequestParam("schema") String schema,
                                                 @RequestParam("token") String token);

    @GetMapping("/user/u/{username}")
    Response<UserDto> getUser(@PathVariable("username") String username);

    @GetMapping("/user/id/{userId}")
    Response<UserDto> getUserById(@PathVariable("userId") Long userId);

    default Response<RestUserDetails> getRestUserDetails(SecurityToken token) {
        return getRestUserDetails(token.getSchema(), token.getToken());
    }

}
