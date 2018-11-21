package com.harmony.kindless.security.service.impl;

import com.harmony.kindless.apis.domain.security.Authority;
import com.harmony.kindless.security.repository.AuthorityRepository;
import com.harmony.kindless.security.service.AuthorityService;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;

/**
 * @author wuxii
 */
public class AuthorityServiceImpl extends ServiceSupport<Authority, Long> implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    protected QueryableRepository<Authority, Long> getRepository() {
        return authorityRepository;
    }

    @Override
    protected Class<Authority> getDomainClass() {
        return Authority.class;
    }

}
