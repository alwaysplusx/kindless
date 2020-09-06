package com.kindless.user.service.impl;

import com.kindless.core.service.ServiceSupport;
import com.kindless.domain.user.UserAuthority;
import com.kindless.user.repository.UserAuthorityRepository;
import com.kindless.user.service.UserAuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wuxii
 */
@RequiredArgsConstructor
@Service
public class UserAuthorityServiceImpl extends ServiceSupport<UserAuthority> implements UserAuthorityService {

    private final UserAuthorityRepository userAuthorityRepository;

    @Override
    public List<String> getUserAuthorities(Long userId) {
        return null;
    }

    @Override
    protected PagingAndSortingRepository<UserAuthority, Long> getRepository() {
        return userAuthorityRepository;
    }
}
