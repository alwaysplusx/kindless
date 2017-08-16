package com.harmony.kindless.oauth.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.harmony.kindless.domain.domain.User;
import com.harmony.kindless.oauth.domain.ClientInfo;
import com.harmony.kindless.oauth.repository.ClientInfoRepository;
import com.harmony.kindless.util.SecurityUtils;
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

    public ClientInfo register(ClientInfo clientInfo) {
        clientInfo.setUser(new User(SecurityUtils.getUserId()));
        clientInfo.setExpiresIn(-1);
        clientInfo.setClientId(Base64Utils.encodeToString(UUID.randomUUID().toString().getBytes()));
        clientInfo.setClientSecret(Base64Utils.encodeToString(UUID.randomUUID().toString().getBytes()));
        clientInfo.setRefreshTime(new Date());
        return saveOrUpdate(clientInfo);
    }

}
