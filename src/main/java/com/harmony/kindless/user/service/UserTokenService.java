package com.harmony.kindless.user.service;

import com.harmony.kindless.apis.domain.user.UserToken;
import com.harmony.kindless.apis.dto.UserTokenClaims;
import com.harmony.umbrella.data.service.Service;

/**
 * @author wuxii
 */
public interface UserTokenService extends Service<UserToken, Long> {

    String generateTokenValue(UserTokenClaims utc);

}
