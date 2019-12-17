package com.kindless.moment.graphql.fetcher;

import com.kindless.moment.domain.Moment;
import com.kindless.moment.graphql.type.MethodType;
import graphql.language.FieldDefinition;
import graphql.language.Type;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLFieldDefinition;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;

/**
 * @author wuxin
 */
@Slf4j
public class JpaDataFetcher implements DataFetcher {

    private EntityManager entityManager;

    public JpaDataFetcher() {
    }

    public JpaDataFetcher(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Object get(DataFetchingEnvironment environment) {
        GraphQLFieldDefinition graphQLFieldDefinition = environment.getFieldDefinition();
        FieldDefinition fieldDefinition = graphQLFieldDefinition.getDefinition();
        Type fieldType = fieldDefinition.getType();
        if (fieldType instanceof MethodType) {
        }
        log.info("fetcher data by definition: {}", fieldType);
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
