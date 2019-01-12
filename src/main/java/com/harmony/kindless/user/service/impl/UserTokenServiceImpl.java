package com.harmony.kindless.user.service.impl;

import com.harmony.kindless.apis.domain.user.UserToken;
import com.harmony.kindless.user.repository.UserTokenRepository;
import com.harmony.kindless.user.service.UserTokenService;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;
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
