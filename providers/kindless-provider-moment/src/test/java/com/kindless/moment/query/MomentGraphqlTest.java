package com.kindless.moment.query;

import com.kindless.moment.graphql.GraphqlBuilder;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author wuxin
 */
@Slf4j
public class MomentGraphqlTest {

    private static GraphQL graphQL;

    @BeforeClass
    public static void setup() {
        GraphQLObjectType graphqlQuery = GraphqlBuilder.buildGraphqlQuery(MomentGraphql.class);
        graphQL = GraphQL.newGraphQL(GraphQLSchema.newSchema().query(graphqlQuery).build()).build();
    }

    @Test
    public void testQuery() {
        ExecutionResult result = graphQL.execute("query { moment(id: \"1\") { userId content } }");
        log.info("execute query, result: {}", result);
    }

}
