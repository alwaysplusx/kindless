package com.harmony.kindless.oauth.service;

import com.harmony.kindless.oauth.domain.ClientInfo;
import com.harmony.umbrella.data.service.Service;

/**
 * @author wuxii@foxmail.com
 */
public interface ClientInfoService extends Service<ClientInfo, String> {

    /**
     * 创建client info
     * 
     * @param ci
     *            client info
     * @return client info
     */
    ClientInfo register(ClientInfo ci);

}
