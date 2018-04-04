package com.harmony.kindless.oauth.service.impl;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harmony.kindless.core.domain.ClientInfo;
import com.harmony.kindless.oauth.repository.ClientInfoRepository;
import com.harmony.kindless.oauth.service.ClientInfoService;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;

/**
 * @author wuxii@foxmail.com
 */
@Service
public class ClientInfoServiceImpl extends ServiceSupport<ClientInfo, String> implements ClientInfoService {

    @Autowired
    private ClientInfoRepository clientInfoRepository;

    /**
     * 注册第三方应用程序
     * 
     * @param clientInfo
     *            第三方应用程序
     * @return 保存后的第三方应用程序
     */
    @Override
    public ClientInfo register(ClientInfo clientInfo) {
        // UserPrincipal up = SecurityUtils.getUserPrincipal();
        // clientInfo.setOwner(new User((Long) up.getIdentity()));
        clientInfo.setExpiresIn(-1);
        clientInfo.setClientSecret(generateClientSecret());
        clientInfo.setRefreshTime(new Date());
        clientInfo = saveOrUpdate(clientInfo);
        return saveOrUpdate(clientInfo);
    }

    /**
     * 重置第三方应用程序clientSecret
     * 
     * @param clientId
     *            第三方应用程序id
     */
    public void resetClientSecret(String clientId) {
        ClientInfo clientInfo = findById(clientId);
        clientInfo.setClientSecret(generateClientSecret());
        clientInfo.setRefreshTime(new Date());
        saveOrUpdate(clientInfo);
    }

    /**
     * 随机生成第三方应用程序密码
     * 
     * @return 第三方应用程序密钥
     */
    protected String generateClientSecret() {
        return RandomStringUtils.randomAlphabetic(64);
    }

    @Override
    protected QueryableRepository<ClientInfo, String> getRepository() {
        return clientInfoRepository;
    }
}
