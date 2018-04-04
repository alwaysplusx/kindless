package com.harmony.kindless.core.support;

import org.springframework.beans.factory.Aware;

/**
 * @author wuxii@foxmail.com
 */
public interface SecurityServiceAware extends Aware {

    void setSecurityService(SecurityService securityService);

}
