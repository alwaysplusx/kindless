package com.harmony.kindless.apis.clients.fallback;

import com.harmony.kindless.apis.clients.UserClient;
import com.harmony.kindless.apis.dto.UserSecurityData;
import org.springframework.stereotype.Component;

/**
 * @author wuxii
 */
@Component
public class UserClientFallback implements UserClient {

    @Override
    public UserSecurityData getUserSecurityData(String username, Long userId) {
        return UserSecurityData.builder().userId(0l).username("fallback").build();
    }

}
