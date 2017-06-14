package com.harmony.kindless.oauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harmony.kindless.oauth.domain.ClientInfo;
import com.harmony.kindless.oauth.repository.ClientInfoRepository;

/**
 * @author wuxii@foxmail.com
 */
@Service
public class ClientInfoService {

    @Autowired
    private ClientInfoRepository clientInfoRepository;

    public ClientInfo save(ClientInfo clientInfo) {
        return clientInfoRepository.save(clientInfo);
    }

    public ClientInfo insertDefault() {
        ClientInfo entity = ClientInfo.DEFAULT_APP;
        if (!clientInfoRepository.exists(entity.getId())) {
            return clientInfoRepository.save(ClientInfo.DEFAULT_APP);
        }
        return entity;
    }

}
