package com.kindless.moment;

import com.harmony.umbrella.data.repository.support.QueryableRepositoryFactoryBean;
import com.kindless.moment.domain.Moment;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring.Builder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;

/**
 * @author wuxii
 */
@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = QueryableRepositoryFactoryBean.class)
public class MomentApplication {

    public static void main(String[] args) {
        SpringApplication.run(MomentApplication.class, args);
    }

    @Bean
    public GraphQLSchema graphQLSchema() throws IOException {
        DefaultResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource("classpath:graphql/moment.graphql");
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(resource.getFile());
        RuntimeWiring wiring = buildRuntimeWiring();
        return new SchemaGenerator()
                .makeExecutableSchema(typeRegistry, wiring);
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", this::applyDataFetcher)
                .build();
    }

    private Builder applyDataFetcher(Builder builder) {
        builder.dataFetcher("moment", environment -> new Moment());
        return builder;
    }

}
