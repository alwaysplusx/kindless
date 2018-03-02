package com.harmony.kindless.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harmony.kindless.core.domain.Resource;
import com.harmony.kindless.core.repository.ResourceRepository;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;

/**
 * @author wuxii@foxmail.com
 */
@Service
public class ResourceService extends ServiceSupport<Resource, Long> {

    @Autowired
    private ResourceRepository resourceRepository;

    public void init(Class<?> clazz) {

    }

    @Override
    protected QueryableRepository<Resource, Long> getRepository() {
        return resourceRepository;
    }

}
