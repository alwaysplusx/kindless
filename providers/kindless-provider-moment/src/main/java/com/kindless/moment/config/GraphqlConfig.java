package com.kindless.moment.config;

import com.kindless.moment.repository.MomentRepository;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * @author wuxin
 */
@Slf4j
@Configuration
public class GraphqlConfig {

    private final MomentRepository momentRepository;

    public GraphqlConfig(MomentRepository momentRepository) {
        this.momentRepository = momentRepository;
    }

    @Bean
    public GraphQL graphql() throws IOException {
        return GraphQL.newGraphQL(graphQLSchema()).build();
    }

    @Bean
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
            Integer id = environment.getArgument("id");
            log.info("find moment by id: {}", id);
            return momentRepository.findById(id.longValue());
        });
        return builder;
    }
}
