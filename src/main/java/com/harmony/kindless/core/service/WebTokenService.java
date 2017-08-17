package com.harmony.kindless.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harmony.kindless.core.domain.WebToken;
import com.harmony.kindless.core.repository.WebTokenRepository;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;

/**
 * @author wuxii@foxmail.com
 */
@Service
public class WebTokenService extends ServiceSupport<WebToken, String> {

    @Autowired
    private WebTokenRepository webTokenRepository;

    @Override
    protected QueryableRepository<WebToken, String> getRepository() {
        return webTokenRepository;
    }

}
