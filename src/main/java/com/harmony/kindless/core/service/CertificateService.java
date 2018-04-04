package com.harmony.kindless.core.service;

import com.harmony.kindless.core.domain.Certificate;
import com.harmony.umbrella.data.service.Service;

/**
 * @author wuxii@foxmail.com
 */
public interface CertificateService extends Service<Certificate, Long> {

    /**
     * 根据token的文本key查找获得token
     *
     * @param token
     *            json web token
     * @return token
     */
    Certificate findByToken(String token);

    /**
     * 将token与session id进行绑定
     * 
     * @param token
     *            json web token
     * @param sessionId
     *            session id
     */
    void bindSessionId(String token, String sessionId);

    /**
     * 通过json web token获取session id
     * 
     * @param token
     *            json web token
     * @return session id
     */
    String getSessionId(String token);

}
