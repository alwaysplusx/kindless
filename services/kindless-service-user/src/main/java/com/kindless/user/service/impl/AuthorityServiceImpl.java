package com.kindless.user.service.impl;

import com.kindless.core.service.ServiceSupport;
import com.kindless.domain.user.Authority;
import com.kindless.user.repository.AuthorityRepository;
import com.kindless.user.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

/**
 * @author wuxii
 */
@RequiredArgsConstructor
@Service
public class AuthorityServiceImpl extends ServiceSupport<Authority> implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Override
    protected PagingAndSortingRepository<Authority, Long> getRepository() {
        return authorityRepository;
    }

}
