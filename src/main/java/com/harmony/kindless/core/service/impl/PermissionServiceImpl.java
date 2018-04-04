package com.harmony.kindless.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harmony.kindless.core.domain.Permission;
import com.harmony.kindless.core.repository.PermissionRepository;
import com.harmony.kindless.core.service.PermissionService;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;

/**
 * @author wuxii@foxmail.com
 */
@Service
public class PermissionServiceImpl extends ServiceSupport<Permission, String> implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    protected QueryableRepository<Permission, String> getRepository() {
        return permissionRepository;
    }

}
