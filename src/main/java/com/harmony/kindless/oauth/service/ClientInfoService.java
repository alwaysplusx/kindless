package com.harmony.kindless.oauth.service;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.harmony.kindless.core.domain.User;
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

    /**
     * 注册第三方应用程序
     * 
     * @param clientInfo
     *            第三方应用程序
     * @return 保存后的第三方应用程序
     */
    public ClientInfo register(ClientInfo clientInfo) {
        clientInfo.setUser(new User(SecurityUtils.getUserId()));
        clientInfo.setExpiresIn(-1);
        clientInfo.setClientId(generateClientId());
        clientInfo.setClientSecret(generateClientSecret());
        clientInfo.setRefreshTime(new Date());
        return saveOrUpdate(clientInfo);
    }

    /**
     * 重置第三方应用程序clientSecret
     * 
     * @param clientId
     *            第三方应用程序id
     */
    public void resetClientSecret(String clientId) {
        ClientInfo clientInfo = findOne(clientId);
        clientInfo.setClientSecret(generateClientSecret());
        clientInfo.setRefreshTime(new Date());
        saveOrUpdate(clientInfo);
    }

    /**
     * 随机生成第三方应用程序id
     * 
     * @return 第三方应用程序id
     */
    protected String generateClientId() {
        return RandomStringUtils.randomNumeric(9);
    }

    /**
     * 随机生成第三方应用程序密码
     * 
     * @return 第三方应用程序密钥
     */
    protected String generateClientSecret() {
        return Base64Utils.encodeToString(UUID.randomUUID().toString().getBytes());
    }

}
