package com.harmony.kindless.apis.clients;

import com.harmony.kindless.apis.clients.fallback.UserClientFallback;
import com.harmony.kindless.apis.dto.UserSecurityData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wuxii
 */
@FeignClient(name = Client.KINDLESS_CORE, fallback = UserClientFallback.class)
public interface UserClient extends Client {

    @GetMapping("/security/user")
    UserSecurityData getUserSecurityData(@RequestParam("username") String username, @RequestParam("userId") Long userId);

    default UserSecurityData getUserSecurityData(String username) {
        return getUserSecurityData(username, null);
    }

    default UserSecurityData getUserSecurityData(Long userId) {
        return getUserSecurityData(null, userId);
    }

}
