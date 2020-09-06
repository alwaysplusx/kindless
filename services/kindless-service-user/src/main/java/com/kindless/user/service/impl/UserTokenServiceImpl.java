package com.kindless.user.service.impl;

import com.kindless.core.service.ServiceSupport;
import com.kindless.domain.user.UserToken;
import com.kindless.user.repository.UserTokenRepository;
import com.kindless.user.service.UserTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

/**
 * @author wuxii
 */
@RequiredArgsConstructor
@Service
public class UserTokenServiceImpl extends ServiceSupport<UserToken> implements UserTokenService {

    private final UserTokenRepository userTokenRepository;

    @Override
    protected PagingAndSortingRepository<UserToken, Long> getRepository() {
        return userTokenRepository;
    }

}
