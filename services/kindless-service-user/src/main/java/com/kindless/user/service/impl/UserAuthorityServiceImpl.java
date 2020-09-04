package com.kindless.user.service.impl;

import com.kindless.core.service.ServiceSupport;
import com.kindless.domain.user.UserAuthority;
import com.kindless.user.service.UserAuthorityService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wuxii
 */
@Service
public class UserAuthorityServiceImpl extends ServiceSupport<UserAuthority, Long> implements UserAuthorityService {

    @Override
    public List<String> getUserAuthorities(Long userId) {
        return null;
    }

}
