package com.harmony.kindless.shiro.support;

import org.springframework.beans.factory.Aware;

import com.harmony.kindless.core.service.SecurityService;

/**
 * @author wuxii@foxmail.com
 */
public interface SecurityServiceAware extends Aware {

    void setSecurityService(SecurityService securityService);

}
