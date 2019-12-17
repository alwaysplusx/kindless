package com.kindless.moment;

import com.kindless.moment.domain.Moment;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring.Builder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * @author wuxin
 */
@Slf4j
public class MomentApplicationTest {

    private GraphQL graphQL;

    @Before
    public void setup() throws IOException {
        GraphQLSchema graphQLSchema = graphQLSchema();
        graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    @Test
    public void testFetchMoment() {
        ExecutionResult result = graphQL.execute("query { moment(id: \"1\") {userId} }");
        Object data = result.getData();
        log.info("fetch result: {}", data);
    }

    public GraphQLSchema graphQLSchema() throws IOException {
        DefaultResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource("classpath:graphql/moment.graphql");
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(resource.getFile());
        RuntimeWiring wiring = buildRuntimeWiring();
        return new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", this::applyDataFetcher)
                .build();
    }

    private Builder applyDataFetcher(Builder builder) {
        builder.dataFetcher("moment", environment -> {
            log.info("fetcher moment: {}", environment);
            Moment moment = new Moment();
            moment.setId(1L);
            moment.setUserId(1L);
            return moment;
        });
        return builder;
    }

}
