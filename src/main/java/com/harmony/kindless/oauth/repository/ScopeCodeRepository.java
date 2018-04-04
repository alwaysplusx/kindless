package com.harmony.kindless.oauth.repository;

import com.harmony.kindless.oauth.domain.ScopeCode;
import com.harmony.umbrella.data.repository.QueryableRepository;

/**
 * @author wuxii@foxmail.com
 */
public interface ScopeCodeRepository extends QueryableRepository<ScopeCode, Long> {

    ScopeCode findByCode(String code);

}
