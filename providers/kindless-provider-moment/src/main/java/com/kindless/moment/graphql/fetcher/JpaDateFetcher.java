package com.kindless.moment.graphql.fetcher;

import com.kindless.moment.domain.Moment;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;

/**
 * @author wuxin
 */
@Slf4j
public class JpaDateFetcher implements DataFetcher {

    private EntityManager entityManager;

    public JpaDateFetcher() {
    }

    public JpaDateFetcher(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Object get(DataFetchingEnvironment environment) {
        log.info("do some data fetch by jpa entity manager", entityManager);
        return Moment
                .builder()
                .content("moment content")
                .userId(10001L)
                .source("test-env")
                .status(0)
                .type(-1)
                .build();
    }

}
