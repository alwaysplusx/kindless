package com.kindless.moment.graphql;

import graphql.schema.GraphQLObjectType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class GraphqlBuilderTest {

    public GraphqlBuilder graphqlBuilder = new GraphqlBuilder();

    @Test
    public void testBuildMode() {
        Class<User> modelClass = User.class;
        GraphQLObjectType graphQLObjectType = graphqlBuilder.graphqlObject(modelClass);
        log.info("build result model type, {}", graphQLObjectType);
    }

}
