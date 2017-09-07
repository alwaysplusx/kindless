package com.harmony.kindless.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harmony.kindless.core.domain.Role;
import com.harmony.kindless.core.repository.RoleRepository;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;

/**
 * @author wuxii@foxmail.com
 */
@Service
public class RoleService extends ServiceSupport<Role, String> {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    protected QueryableRepository<Role, String> getRepository() {
        return roleRepository;
    }

}
