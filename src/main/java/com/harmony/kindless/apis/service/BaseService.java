package com.harmony.kindless.apis.service;

import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;

import java.io.Serializable;

/**
 * @author wuxii
 */
public class BaseService<T, ID extends Serializable> extends ServiceSupport<T, ID> {


    protected final QueryableRepository<T, ID> repository;

    public BaseService(QueryableRepository<T, ID> repository) {
        this.repository = repository;
    }
    
    @Override
    protected QueryableRepository<T, ID> getRepository() {
        return repository;
    }

    @Override
    protected Class<T> getDomainClass() {
        return repository.getDomainClass();
    }

}
