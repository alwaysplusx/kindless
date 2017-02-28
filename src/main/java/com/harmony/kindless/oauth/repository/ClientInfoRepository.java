package com.harmony.kindless.oauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harmony.kindless.oauth.domain.ClientInfo;

/**
 * @author wuxii@foxmail.com
 */
public interface ClientInfoRepository extends JpaRepository<ClientInfo, String> {

}
