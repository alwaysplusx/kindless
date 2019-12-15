package com.kindless.moment.graphql;

import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLOutputType;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import static com.kindless.moment.graphql.GraphqlBuilder.*;

public class GraphqlBuildContext {

    private Map<Class<?>, GraphQLOutputType> graphqlObjectTypes = new ConcurrentHashMap<>();

    public GraphQLOutputType buildGraphqlObject(Class<?> type) {
        if (isPrimitiveType(type)) {
            throw new IllegalArgumentException("primitive type class " + type);
        }
        GraphQLOutputType graphqlObject = graphqlObjectTypes.get(type);
        if (graphqlObject == null) {
            GraphQLObjectType.Builder builder = GraphQLObjectType
                    .newObject()
                    .name(type.getSimpleName())
                    .description(graphTypeName(type))
                    .definition(typeDefinition(type));
            GraphqlFields graphqlFields = getGraphqlFields(type);
            for (Field primitiveField : graphqlFields.primitiveFields) {
                builder.field(buildGraphqlObjectField(primitiveField));
            }
            graphqlObjectTypes.put(type, builder.build());
            graphqlObject = builder.build();
        }
        return graphqlObject;
    }

    private GraphQLFieldDefinition buildGraphqlObjectField(Field field) {
        GraphQLOutputType graphqlFieldType = graphqlObjectFieldType(field);
        String graphFieldName = graphFieldName(field);
        return GraphQLFieldDefinition
                .newFieldDefinition()
                .name(graphFieldName)
                .type(
                        isArrayOrCollection(field.getType())
                                ? GraphQLList.list(graphqlFieldType)
                                : graphqlFieldType
                )
                .description(graphFieldName)
                .definition(fieldDefinition(field))
                .build();
    }

    private GraphQLOutputType graphqlObjectFieldType(Field field) {
        Class<?> fieldType = field.getType();
        Class<?> fieldActualType = getFieldActualType(field);
        GraphQLOutputType graphqlObjectField = graphqlObjectTypes.get(fieldActualType);
        if (graphqlObjectField == null) {
            graphqlObjectField =
                    isPrimitiveType(fieldActualType)
                            ? graphPrimitiveType(fieldActualType)
                            : buildGraphqlObject(fieldActualType);
            graphqlObjectTypes.put(fieldActualType, graphqlObjectField);
        }
        return isArrayOrCollection(fieldType)
                ? GraphQLList.list(graphqlObjectField)
                : graphqlObjectField;
    }

    private GraphqlFields getGraphqlFields(Class<?> type) {
        GraphqlFields graphqlFields = new GraphqlFields();
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                (isPrimitiveType(field.getType()) || isPrimitiveArrayOrCollectionField(field) ?
                        graphqlFields.primitiveFields :
                        graphqlFields.complexFields
                ).add(field);
            }
        }
        return graphqlFields;
    }

    private static class GraphqlFields {
        List<Field> primitiveFields = new ArrayList<>();
        List<Field> complexFields = new ArrayList<>();
    }

    private static class LazyLoadGraphQLOutputType implements GraphQLOutputType {

        private Supplier<GraphQLOutputType> graphQLOutputTypeSupplier;

        @Override
        public String getName() {
            return graphQLOutputTypeSupplier.get().getName();
        }

    }

}
