package com.harmony.kindless.apis.service;

import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;

import java.io.Serializable;

/**
 * @author wuxii
 */
public class GenericServiec<T, ID extends Serializable> extends ServiceSupport<T, ID> {

    @Override
    protected QueryableRepository<T, ID> getRepository() {
        return null;
    }

    @Override
    protected Class<T> getDomainClass() {
        return null;
    }

}