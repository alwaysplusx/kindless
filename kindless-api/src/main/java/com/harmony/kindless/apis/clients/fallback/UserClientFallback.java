package com.harmony.kindless.apis.clients.fallback;

import com.harmony.kindless.apis.clients.UserClient;
import com.harmony.kindless.apis.support.RestUserDetails;
import com.harmony.umbrella.web.Response;
import org.springframework.stereotype.Component;

/**
 * @author wuxii
 */
@Component
public class UserClientFallback implements UserClient {

    @Override
    public Response<RestUserDetails> getRestUserDetails(String token, String schema) {
        return null;
    }

}
