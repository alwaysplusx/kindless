package com.harmony.kindless.wechat.repository;

import com.harmony.kindless.apis.domain.weixin.WeixinMpConfig;
import com.harmony.umbrella.data.repository.QueryableRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuxii
 */
@Repository
public interface WeixinMpConfigRepository extends QueryableRepository<WeixinMpConfig, Long> {

}
