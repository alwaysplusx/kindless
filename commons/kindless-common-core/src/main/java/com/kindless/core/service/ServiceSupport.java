package com.kindless.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author wuxin
 */
public abstract class ServiceSupport<T> implements Service<T> {

    protected abstract PagingAndSortingRepository<T, Long> getRepository();

    @Override
    public T save(T entity) {
        return getRepository().save(entity);
    }

    @Override
    public Iterable<T> saveAll(Iterable<T> entities) {
        return getRepository().saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        getRepository().deleteById(id);
    }

    @Override
    public void delete(T entity) {
        getRepository().delete(entity);
    }

    @Override
    public void deleteAll(Iterable<T> entities) {
        getRepository().deleteAll(entities);
    }

    @Override
    public long count() {
        return getRepository().count();
    }

    @Override
    public Iterable<T> findAllById(Iterable<Long> ids) {
        return getRepository().findAllById(ids);
    }

    @Override
    public Optional<T> findById(Long id) {
        return getRepository().findById(id);
    }

    @Override
    public List<T> findAll(Sort sort) {
        Iterable<T> result = getRepository().findAll(sort);
        return StreamSupport.stream(result.spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public List<T> findAll(Pageable pageable) {
        return getRepository().findAll(pageable).getContent();
    }

    @Override
    public Page<T> findPage(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

}
