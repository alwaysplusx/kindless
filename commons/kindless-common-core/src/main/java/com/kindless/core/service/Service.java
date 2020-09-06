package com.kindless.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

/**
 * @author wuxin
 */
public interface Service<T> {

    T save(T entity);

    Iterable<T> saveAll(Iterable<T> entities);

    void deleteById(Long id);

    void delete(T entity);

    void deleteAll(Iterable<T> entities);

    long count();

    Iterable<T> findAllById(Iterable<Long> ids);

    Optional<T> findById(Long id);

    List<T> findAll(Sort sort);

    List<T> findAll(Pageable pageable);

    Page<T> findPage(Pageable pageable);

}
