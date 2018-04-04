package com.harmony.kindless.core.support;

import com.harmony.kindless.core.domain.Certificate;
import com.harmony.kindless.core.domain.ClientInfo;
import com.harmony.kindless.core.domain.User;
import com.harmony.kindless.jwt.RequestOriginProperties;

/**
 * @author wuxii@foxmail.com
 */
public interface SecurityService {

    /**
     * 系统给用户授权(不含有第三方)
     *
     * @param user 系统用户
     * @param rop  用户所在的设备源
     * @return token certificate
     */
    Certificate grant(User user, RequestOriginProperties rop);

    /**
     * 用户给第三方客户端授权
     *
     * @param user 用户, 资源拥有者
     * @param ci   第三方客户端
     * @param rop  第三方所在的域限制
     * @return token certificate
     */
    Certificate grant(User user, ClientInfo ci, RequestOriginProperties rop);

    /**
     * 系统给第三方授权
     *
     * @param ci  第三方
     * @param rop 第三方所在的域限制
     * @return token certificate
     */
    Certificate grant(ClientInfo ci, RequestOriginProperties rop);

}
