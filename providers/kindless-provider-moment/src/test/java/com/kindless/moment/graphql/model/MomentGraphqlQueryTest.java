package com.kindless.moment.graphql.model;

import com.kindless.moment.domain.Moment;
import com.kindless.moment.graphql.annotation.GraphqlField;
import com.kindless.moment.graphql.annotation.GraphqlParam;
import com.kindless.moment.graphql.annotation.GraphqlQuery;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.Scalars;
import graphql.language.Description;
import graphql.language.FieldDefinition;
import graphql.language.InputValueDefinition;
import graphql.language.SourceLocation;
import graphql.schema.*;
import graphql.schema.GraphQLFieldDefinition.Builder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MomentGraphqlQueryTest {

    @Test
    public void test() {
        Class<MomentGraphqlQuery> queryType = MomentGraphqlQuery.class;
        GraphQLSchema schema = GraphQLSchema
                .newSchema()
                .query(
                        GraphQLObjectType
                                .newObject()
                                .name("Query")
                                .field(buildQueryType(queryType))
                                .build()
                )
                .build();

        GraphQL graphQL = GraphQL.newGraphQL(schema).build();
        ExecutionResult result = graphQL.execute("query { moment(id: \"1\") {id userId content} }");
        log.info("graphql execute result: {}", result);

    }

    public GraphQLFieldDefinition buildQueryType(Class<?> type) {
        GraphqlQuery ann = type.getAnnotation(GraphqlQuery.class);
        Builder builder = GraphQLFieldDefinition
                .newFieldDefinition()
                .name(ann == null || !StringUtils.hasText(ann.value()) ? type.getSimpleName() : ann.value())
                .description(ann == null ? null : ann.description())
                .dataFetcher(new DataFetcher<Moment>() {
                    @Override
                    public Moment get(DataFetchingEnvironment environment) {
                        return Moment.builder().userId(1L).content("example content").build();
                    }
                })
                .type(buildQueryModelType(type))
                .definition(buildQueryModelDefinition(type));

        buildQueryArguments(type)
                .forEach(builder::argument);

        return builder.build();
    }

    private List<GraphQLArgument> buildQueryArguments(Class<?> type) {
        List<GraphQLArgument> arguments = new ArrayList<>();
        for (Field field : type.getDeclaredFields()) {
            GraphqlParam ann = AnnotationUtils.getAnnotation(field, GraphqlParam.class);
            if (ann != null) {
                arguments.add(
                        GraphQLArgument
                                .newArgument()
                                .name(StringUtils.hasText(ann.name()) ? ann.name() : field.getName())
                                .type(argumentInputType(field.getType()))
                                .definition(inputArgumentDefinition(ann, field))
                                .build()
                );
            }
        }
        return arguments;
    }

    private InputValueDefinition inputArgumentDefinition(GraphqlParam ann, Field field) {
        return null;
    }

    public FieldDefinition buildQueryModelDefinition(Class<?> type) {
        FieldDefinition modelDefinition = new FieldDefinition(type.getSimpleName());
        modelDefinition.setDescription(typeDescription(type));
        return modelDefinition;
    }

    public GraphQLObjectType buildQueryModelType(Class<?> type) {
        GraphqlQuery ann = type.getAnnotation(GraphqlQuery.class);
        GraphQLObjectType.Builder builder = GraphQLObjectType
                .newObject()
                .name(ann == null ? type.getSimpleName() : ann.value());
        for (Field field : type.getDeclaredFields()) {
            builder.field(buildFieldDefinition(field));
        }
        return builder.build();
    }

    public GraphQLFieldDefinition buildFieldDefinition(Field field) {
        GraphqlField ann = field.getAnnotation(GraphqlField.class);
        return GraphQLFieldDefinition
                .newFieldDefinition()
                .name(field.getName())
                .type(scalarType(field.getType()))
                .description(ann == null ? null : ann.name())
                .build();
    }

    private static GraphQLInputType argumentInputType(Class<?> type) {
        return scalarType(type);
//        InputObjectTypeDefinition definition = new InputObjectTypeDefinition(type.getSimpleName());
//        definition.setDescription(typeDescription(type));
//        return GraphQLInputObjectType
//                .newInputObject()
//                .name(type.getSimpleName())
//                .description(type.getName())
//                .definition(definition)
//                .build();
    }

    private static GraphQLScalarType scalarType(Class<?> type) {
        return TYPES.get(type);
    }

    private static Description typeDescription(Class<?> type) {
        SourceLocation sourceLocation = new SourceLocation(-1, -1, type.getName());
        return new Description(type.getSimpleName(), sourceLocation, false);
    }

    private static final Map<Class, GraphQLScalarType> TYPES = new HashMap<>();

    static {
        TYPES.put(Integer.class, Scalars.GraphQLInt);
        TYPES.put(int.class, Scalars.GraphQLInt);
        TYPES.put(Long.class, Scalars.GraphQLBigInteger);
        TYPES.put(BigDecimal.class, Scalars.GraphQLBigDecimal);
        TYPES.put(BigInteger.class, Scalars.GraphQLBigInteger);
        TYPES.put(Double.class, Scalars.GraphQLBigDecimal);
        TYPES.put(String.class, Scalars.GraphQLString);
        TYPES.put(Boolean.class, Scalars.GraphQLBoolean);
        TYPES.put(Character.class, Scalars.GraphQLChar);
        TYPES.put(Short.class, Scalars.GraphQLShort);
        TYPES.put(Float.class, Scalars.GraphQLFloat);
        TYPES.put(Byte.class, Scalars.GraphQLByte);
    }

}
