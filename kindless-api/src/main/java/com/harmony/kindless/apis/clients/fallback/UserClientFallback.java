package com.harmony.kindless.apis.clients.fallback;

import com.harmony.kindless.apis.clients.UserClient;
import com.harmony.kindless.apis.support.RestUserDetails;
import org.springframework.stereotype.Component;

/**
 * @author wuxii
 */
@Component
public class UserClientFallback implements UserClient {

	@Override
	public RestUserDetails getRestUserDetails(String token, String schema) {
		return null;
	}

}
