package com.harmony.kindless.oauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harmony.kindless.oauth.domain.ClientInfo;
import com.harmony.kindless.oauth.repository.ClientInfoRepository;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;

/**
 * @author wuxii@foxmail.com
 */
@Service
public class ClientInfoService extends ServiceSupport<ClientInfo, String> {

    @Autowired
    private ClientInfoRepository clientInfoRepository;

    @Override
    protected QueryableRepository<ClientInfo, String> getRepository() {
        return clientInfoRepository;
    }

}
