package com.kindless.moment.graphql.fetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import javax.persistence.EntityManager;

/**
 * @author wuxin
 */
public class JpaDateFetcher implements DataFetcher {

    private EntityManager entityManager;

    @Override
    public Object get(DataFetchingEnvironment environment) {
        return null;
    }

}
