package com.kindless.moment.graphql;

import graphql.schema.GraphQLObjectType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class GraphqlBuildContextTest {

    private GraphqlBuildContext buildContext = new GraphqlBuildContext();

    @Test
    public void testObjectTypeBuild() {
        Class<User> userClass = User.class;
        GraphQLObjectType objectType = buildContext.buildGraphqlObject(userClass);
        log.info("build result: {}", objectType);
    }

}