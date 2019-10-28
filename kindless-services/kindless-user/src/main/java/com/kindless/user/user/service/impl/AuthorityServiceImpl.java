package com.kindless.user.user.service.impl;

import com.kindless.user.domain.Authority;
import com.kindless.user.user.repository.AuthorityRepository;
import com.kindless.user.user.service.AuthorityService;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;
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
