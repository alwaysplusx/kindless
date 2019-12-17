package com.kindless.moment.graphql;

import com.harmony.umbrella.util.StringUtils;
import com.kindless.moment.graphql.annotation.*;
import com.kindless.moment.graphql.fetcher.JpaDataFetcher;
import com.kindless.moment.graphql.type.MethodType;
import graphql.Scalars;
import graphql.language.*;
import graphql.schema.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class GraphqlBuilder {

    // FIXME support graphql import

    public static GraphQLObjectType buildGraphqlQuery(Class<?> queryClass) {
        String graphqlQueryName = graphqlQueryName(queryClass, queryClass.getSimpleName());
        GraphqlMetadata metadata = GraphqlMetadata.of(queryClass);
        return GraphQLObjectType
                .newObject()
                .name(graphqlQueryName)
                .fields(buildGraphqlQueryFieldsDefinition(metadata))
                .definition(objectDefinition(queryClass))
                .build();
    }

    public static GraphQLObjectType buildGraphqlObject(Class<?> typeClass) {
        GraphqlMetadata metadata = GraphqlMetadata.of(typeClass);
        String graphqlObjectName = graphqlObjectName(typeClass);
        // add custom description
        return GraphQLObjectType
                .newObject()
                .name(graphqlObjectName)
                .description(typeClass.getSimpleName())
                .definition(objectDefinition(typeClass))
                .fields(buildGraphqlObjectFieldsDefinition(metadata))
                .build();
    }

    private static List<GraphQLFieldDefinition> buildGraphqlQueryFieldsDefinition(GraphqlMetadata metadata) {
        return metadata
                .methods
                .stream()
                .map(GraphqlBuilder::buildGraphqlQueryFieldDefinition)
                .collect(Collectors.toList());
    }

    private static List<GraphQLFieldDefinition> buildGraphqlObjectFieldsDefinition(GraphqlMetadata metadata) {
        return metadata
                .fields
                .stream()
                .map(GraphqlBuilder::buildGraphqlObjectFieldDefinition)
                .collect(Collectors.toList());
    }

    private static GraphQLFieldDefinition buildGraphqlQueryFieldDefinition(Method method) {
        String graphqlQueryName = graphqlQueryName(method, method.getName());
        return GraphQLFieldDefinition
                .newFieldDefinition()
                .name(graphqlQueryName)
                .type(buildGraphqlObject(method.getReturnType()))
                .argument(buildGraphqlQueryArguments(method))
                // FIXME 定制fetcher
                .dataFetcherFactory(new DataFetcherFactory() {
                    @Override
                    public DataFetcher get(DataFetcherFactoryEnvironment environment) {
                        return new JpaDataFetcher();
                    }
                })
                .definition(methodDefinition(method))
                .build();
    }

    private static List<GraphQLArgument> buildGraphqlQueryArguments(Method method) {
        return Arrays
                .stream(method.getParameters())
                .filter(GraphqlBuilder::isNonGraphqlIgnore)
                .map(GraphqlBuilder::buildGraphqlQueryArgument)
                .collect(Collectors.toList());
    }

    private static GraphQLArgument buildGraphqlQueryArgument(Parameter parameter) {
        String paramName = graphqlParamName(parameter);
        return GraphQLArgument
                .newArgument()
                .name(paramName)
                .type(buildGraphqlQueryParameterType(parameter))
                .definition(parameterDefinition(parameter))
                .build();
    }

    private static GraphQLFieldDefinition buildGraphqlObjectFieldDefinition(Field field) {
        String graphqlFieldName = graphqlFieldName(field);
        // add custom description
        return GraphQLFieldDefinition
                .newFieldDefinition()
                .name(graphqlFieldName)
                .type(buildFieldGraphqlObjectType(field))
                .definition(fieldDefinition(field))
                .description(field.getName())
                .build();
    }

    private static GraphQLOutputType buildFieldGraphqlObjectType(Field field) {
        Class<?> fieldType = field.getType();
        boolean isArrayField = isArrayOrCollection(fieldType);
        Class<?> fieldActualType = isArrayField ? getFieldActualType(field) : fieldType;
        GraphQLOutputType type = PRIMITIVE_TYPES.get(fieldActualType);
        if (type == null) {
            type = GraphQLTypeReference.typeRef(graphqlObjectName(fieldActualType));
        }
        return isArrayField ? GraphQLList.list(type) : type;
    }

    private static GraphQLInputType buildGraphqlQueryParameterType(Parameter parameter) {
        Class<?> parameterType = parameter.getType();
        boolean isArrayParameter = isArrayOrCollection(parameterType);
        Class<?> parameterActualType = isArrayParameter ? getParameterActualType(parameter) : parameterType;
        GraphQLInputType type = PRIMITIVE_TYPES.get(parameterActualType);
        if (type == null) {
            type = GraphQLTypeReference.typeRef(graphqlObjectName(parameterActualType));
        }
        return isArrayParameter ? GraphQLList.list(type) : type;
    }

    private static ObjectTypeDefinition objectDefinition(Class<?> type) {
        ObjectTypeDefinition definition = new ObjectTypeDefinition(type.getSimpleName());
        definition.setDescription(typeDescription(type));
        definition.setSourceLocation(typeSourceLocation(type));
        List<FieldDefinition> fieldDefinitions = definition.getFieldDefinitions();
        for (Field field : typeFields(type)) {
            fieldDefinitions.add(fieldDefinition(field));
        }
        return definition;
    }

    private static FieldDefinition fieldDefinition(Field field) {
        FieldDefinition fieldDefinition = new FieldDefinition(field.getName());
        fieldDefinition.setDescription(fieldDescription(field));
        fieldDefinition.setType(fieldType(field));
        fieldDefinition.setSourceLocation(fieldSourceLocation(field));
        return fieldDefinition;
    }

    private static FieldDefinition methodDefinition(Method method) {
        String graphqlQueryName = graphqlQueryName(method, method.getName());
        FieldDefinition definition = new FieldDefinition(graphqlQueryName);
        definition.setDescription(methodDescription(method));
        definition.setType(methodType(method));
        definition.setSourceLocation(methodSourceLocation(method));
        return definition;
    }

    private static InputValueDefinition parameterDefinition(Parameter parameter) {
        InputValueDefinition definition = new InputValueDefinition(parameter.getName());
        definition.setDescription(parameterDescription(parameter));
        definition.setType(parameterType(parameter));
        definition.setSourceLocation(parameterSourceLocation(parameter));
        return definition;
    }

    private static Description methodDescription(Method method) {
        String graphqlQueryName = graphqlQueryName(method, method.getName());
        return new Description(graphqlQueryName, methodSourceLocation(method), false);
    }

    private static graphql.language.Type methodType(Method method) {
        return new MethodType(method);
    }

    private static SourceLocation methodSourceLocation(Method method) {
        return null;
    }

    private static Description typeDescription(Class<?> type) {
        return new Description(type.getSimpleName(), typeSourceLocation(type), false);
    }

    private static graphql.language.Type fieldType(Field field) {
        Class<?> fieldActualType = getFieldActualType(field);
        TypeName fieldTypeName = new TypeName(fieldActualType.getName());
        return isArrayOrCollection(field.getType()) ? new ListType(fieldTypeName) : fieldTypeName;
    }

    private static graphql.language.Type parameterType(Parameter parameter) {
        Class<?> parameterActualType = getParameterActualType(parameter);
        TypeName parameterTypeName = new TypeName(parameterActualType.getName());
        return isArrayOrCollection(parameter.getType()) ? new ListType(parameterTypeName) : parameterTypeName;
    }

    private static Description parameterDescription(Parameter parameter) {
        return new Description(parameter.getName(), parameterSourceLocation(parameter), false);
    }

    private static SourceLocation parameterSourceLocation(Parameter parameter) {
        return new SourceLocation(-1, -1, parameter.getName());
    }

    private static SourceLocation typeSourceLocation(Class<?> type) {
        return new SourceLocation(-1, -1, type.getName());
    }

    private static Description fieldDescription(Field field) {
        return new Description(field.getName(), fieldSourceLocation(field), false);
    }

    private static SourceLocation fieldSourceLocation(Field field) {
        return new SourceLocation(-1, -1, field.getDeclaringClass().getName() + "." + field.getName());
    }

    private static List<Field> typeFields(Class<?> type) {
        return Stream.of(type.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .collect(Collectors.toList());
    }

    private static boolean isArrayOrCollection(Class<?> type) {
        return type.isArray() || Collection.class.isAssignableFrom(type);
    }

    private static Class<?> getParameterActualType(Parameter parameter) {
        Class<?> parameterType = parameter.getType();
        if (parameterType.isArray()) {
            return parameterType.getComponentType();
        }
        return isArrayOrCollection(parameterType) ? Object.class : parameterType;
    }

    @SuppressWarnings("Duplicates")
    private static Class<?> getFieldActualType(Field field) {
        Class<?> fieldType = field.getType();
        if (fieldType.isArray()) {
            return fieldType.getComponentType();
        }
        java.lang.reflect.Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) genericType).getActualTypeArguments()[0];
        }
        return fieldType;
    }

    private static String graphqlFieldName(Field field) {
        GraphqlField ann = AnnotationUtils.getAnnotation(field, GraphqlField.class);
        return StringUtils.getFirstNotBlank(ann != null ? ann.name() : null, field.getName());
    }

    static String graphqlObjectName(Class<?> type) {
        GraphqlObject ann = AnnotationUtils.getAnnotation(type, GraphqlObject.class);
        return StringUtils.getFirstNotBlank(ann != null ? ann.name() : null, type.getSimpleName());
    }

    static String graphqlQueryName(AnnotatedElement annotatedElement, String defaultName) {
        GraphqlQuery ann = AnnotationUtils.getAnnotation(annotatedElement, GraphqlQuery.class);
        return StringUtils.getFirstNotBlank(ann != null ? ann.name() : null, defaultName);
    }

    static String graphqlParamName(Parameter parameter) {
        GraphqlParam ann = AnnotationUtils.getAnnotation(parameter, GraphqlParam.class);
        return StringUtils.getFirstNotBlank(ann != null ? ann.name() : null, parameter.getName());
    }

    static boolean isNonGraphqlIgnore(AnnotatedElement annotatedElement) {
        return AnnotationUtils.getAnnotation(annotatedElement, GraphqlIgnore.class) == null;
    }

    static boolean isGraphqlQuery(AnnotatedElement annotatedElement) {
        return AnnotationUtils.getAnnotation(annotatedElement, GraphqlQuery.class) != null;
    }

    private static final Map<Class, GraphQLScalarType> PRIMITIVE_TYPES = new HashMap<>();

    static {
        PRIMITIVE_TYPES.put(int.class, Scalars.GraphQLInt);
        PRIMITIVE_TYPES.put(long.class, Scalars.GraphQLBigInteger);
        PRIMITIVE_TYPES.put(float.class, Scalars.GraphQLBigDecimal);
        PRIMITIVE_TYPES.put(double.class, Scalars.GraphQLBigDecimal);
        PRIMITIVE_TYPES.put(char.class, Scalars.GraphQLInt);
        PRIMITIVE_TYPES.put(short.class, Scalars.GraphQLInt);
        PRIMITIVE_TYPES.put(byte.class, Scalars.GraphQLInt);
        PRIMITIVE_TYPES.put(boolean.class, Scalars.GraphQLBoolean);

        PRIMITIVE_TYPES.put(Integer.class, Scalars.GraphQLInt);
        PRIMITIVE_TYPES.put(BigDecimal.class, Scalars.GraphQLBigDecimal);
        PRIMITIVE_TYPES.put(Long.class, Scalars.GraphQLBigInteger);
        PRIMITIVE_TYPES.put(BigInteger.class, Scalars.GraphQLBigInteger);
        PRIMITIVE_TYPES.put(Double.class, Scalars.GraphQLBigDecimal);
        PRIMITIVE_TYPES.put(String.class, Scalars.GraphQLString);
        PRIMITIVE_TYPES.put(Boolean.class, Scalars.GraphQLBoolean);
        PRIMITIVE_TYPES.put(Character.class, Scalars.GraphQLChar);
        PRIMITIVE_TYPES.put(Short.class, Scalars.GraphQLShort);
        PRIMITIVE_TYPES.put(Float.class, Scalars.GraphQLFloat);
        PRIMITIVE_TYPES.put(Byte.class, Scalars.GraphQLByte);
    }

    static class GraphqlMetadata {

        static GraphqlMetadata of(Class<?> typeClass) {
            List<Field> fields = Arrays.stream(typeClass.getDeclaredFields())
                    .filter(e -> !Modifier.isStatic(e.getModifiers()))
                    .filter(GraphqlBuilder::isNonGraphqlIgnore)
                    .collect(Collectors.toList());

            boolean classHasGraphqlQueryAnnotation = isGraphqlQuery(typeClass);
            List<Method> methods = Stream.of(ReflectionUtils.getAllDeclaredMethods(typeClass))
                    .filter(GraphqlBuilder::isNonGraphqlIgnore)
                    .filter(e -> !ReflectionUtils.isObjectMethod(e))
                    .filter(e -> classHasGraphqlQueryAnnotation || isGraphqlQuery(e))
                    .filter(e -> !Modifier.isStatic(e.getModifiers()))
                    .collect(Collectors.toList());
            return new GraphqlMetadata(typeClass, fields, methods);
        }

        final Class<?> typeClass;

        final List<Field> fields;

        final List<Method> methods;

        GraphqlMetadata(Class<?> typeClass, List<Field> fields, List<Method> methods) {
            this.typeClass = typeClass;
            this.fields = Collections.unmodifiableList(fields);
            this.methods = Collections.unmodifiableList(methods);
        }

    }


}
