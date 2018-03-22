package com.harmony.kindless.oauth.repository;

import org.springframework.data.jpa.repository.Query;

import com.harmony.kindless.oauth.domain.AccessToken;
import com.harmony.umbrella.data.repository.QueryableRepository;

/**
 * @author wuxii@foxmail.com
 */
public interface AccessTokenRepository extends QueryableRepository<AccessToken, String> {

    @Query("select o from AccessToken o where o.clientInfo.clientId=?1 and o.refreshToken=?2")
    AccessToken findByClientIdAndRefreshToken(String clientId, String refreshToken);

}
