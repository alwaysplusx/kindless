package com.kindless.consumer.user.service.impl;

import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;
import com.kindless.provider.user.domain.UserToken;
import com.kindless.provider.user.repository.UserTokenRepository;
import com.kindless.consumer.user.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuxii
 */
@Service
public class UserTokenServiceImpl extends ServiceSupport<UserToken, Long> implements UserTokenService {

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Override
    protected QueryableRepository<UserToken, Long> getRepository() {
        return userTokenRepository;
    }

    @Override
    protected Class<UserToken> getDomainClass() {
        return UserToken.class;
    }

}
