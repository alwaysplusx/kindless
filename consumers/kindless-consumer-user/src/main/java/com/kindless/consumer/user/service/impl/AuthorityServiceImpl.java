package com.kindless.consumer.user.service.impl;

import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;
import com.kindless.provider.user.domain.Authority;
import com.kindless.provider.user.repository.AuthorityRepository;
import com.kindless.consumer.user.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuxii
 */
@Service
public class AuthorityServiceImpl extends ServiceSupport<Authority, Long> implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Autowired
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
