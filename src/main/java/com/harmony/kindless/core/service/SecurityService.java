package com.harmony.kindless.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuxii@foxmail.com
 */
@Service
public class SecurityService {

    @Autowired
    protected PermissionService permissionService;

    @Autowired
    protected ResourceService resourceService;

    /**
     * 初始化安全系统所设计的资源
     * <ul>
     * <li>1. Resource
     * <li>2. Permission
     * </ul>
     */
    public void initResources() {
    }

}
