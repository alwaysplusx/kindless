package com.harmony.kindless.oauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harmony.kindless.oauth.domain.AccessToken;

/**
 * @author wuxii@foxmail.com
 */
public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {

}
