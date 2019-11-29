package com.harmony.kindless.core.provider;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author wuxin
 */
public interface ProviderClient {

    <T> T getOne(String graphQLString, Class<T> resultType);

    <T> List<T> getAll(String graphQLString, Class<T> resultType);

    <T> Page<T> getPage(String graphQLString, Class<T> resultType);

    default <T> T getOne(String graphQLString) {
        return (T) getOne(graphQLString, Object.class);
    }

    default <T> List<T> getAll(String graphQLString) {
        return (List<T>) getAll(graphQLString, Object.class);
    }

    default <T> Page<T> getPage(String graphQLString) {
        return (Page<T>) getPage(graphQLString, Object.class);
    }

}
