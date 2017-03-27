package com.harmony.kindless.oauth.repository;

import com.harmony.kindless.oauth.domain.AuthorizeCode;
import com.harmony.umbrella.data.repository.QueryableRepository;

/**
 * @author wuxii@foxmail.com
 */
public interface AuthorizeCodeRepository extends QueryableRepository<AuthorizeCode, String> {

    AuthorizeCode findByCodeAndClientId(String code, String clientId);

}
