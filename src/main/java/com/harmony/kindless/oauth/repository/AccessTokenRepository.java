package com.harmony.kindless.oauth.repository;

import com.harmony.kindless.oauth.domain.AccessToken;
import com.harmony.umbrella.data.repository.QueryableRepository;

/**
 * @author wuxii@foxmail.com
 */
public interface AccessTokenRepository extends QueryableRepository<AccessToken, Long> {

    AccessToken findByClientIdAndRefreshToken(String clientId, String refreshToken);

}
