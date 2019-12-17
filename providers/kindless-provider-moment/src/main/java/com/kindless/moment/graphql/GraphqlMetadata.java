package com.kindless.moment.graphql;

import com.kindless.moment.graphql.annotation.Condition;
import com.kindless.moment.graphql.annotation.GraphqlParam;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.kindless.moment.graphql.GraphqlMetadata.fieldActualType;
import static com.kindless.moment.graphql.GraphqlUtils.*;

/**
 * @author wuxin
 */
@Getter
@ToString
@Builder(access = AccessLevel.PACKAGE)
public class GraphqlMetadata {

    @SuppressWarnings("Duplicates")
    static Class<?> fieldActualType(Field field) {
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

    public static GraphqlMetadata of(Class<?> typeClass) {
        String graphqlName = graphqlName(typeClass);
        List<GraphqlFieldMetadata> fields = Arrays.stream(typeClass.getDeclaredFields())
                .filter(e -> !Modifier.isStatic(e.getModifiers()))
                .filter(e -> !hasGraphqlIgnoreAnnotation(e))
                .map(GraphqlFieldMetadata::of)
                .collect(Collectors.toList());
        boolean hasGraphqlQueryAnnotation = hasGraphqlQueryAnnotation(typeClass);
        List<GraphqlMethodMetadata> methods = Stream.of(ReflectionUtils.getAllDeclaredMethods(typeClass))
                .filter(GraphqlBuilder::isNonGraphqlIgnore)
                .filter(e -> !ReflectionUtils.isObjectMethod(e))
                .filter(e -> hasGraphqlQueryAnnotation || hasGraphqlQueryAnnotation(e))
                .filter(e -> !Modifier.isStatic(e.getModifiers()) && Modifier.isAbstract(e.getModifiers()))
                .map(GraphqlMethodMetadata::of)
                .collect(Collectors.toList());
        return GraphqlMetadata
                .builder()
                .name(graphqlName)
                .typeClass(typeClass)
                .fields(fields)
                .methods(methods)
                .build();
    }

    String name;
    Class<?> typeClass;
    List<GraphqlFieldMetadata> fields;
    List<GraphqlMethodMetadata> methods;

}

@Getter
@ToString
@Builder(access = AccessLevel.PACKAGE)
class GraphqlFieldMetadata {

    public static GraphqlFieldMetadata of(Field field) {
        return GraphqlFieldMetadata
                .builder()
                .name(graphqlName(field))
                .field(field)
                .actualType(fieldActualType(field))
                .build();
    }

    boolean array;
    String name;
    Field field;
    Class<?> actualType;

}

@Getter
@ToString
@Builder(access = AccessLevel.PACKAGE)
class GraphqlMethodMetadata {

    public static GraphqlMethodMetadata of(Method method) {
        List<GraphqlParameterMetadata> parameters = Arrays.stream(method.getParameters())
                .filter(e -> !hasGraphqlIgnoreAnnotation(e))
                .map(GraphqlParameterMetadata::of)
                .collect(Collectors.toList());
        return GraphqlMethodMetadata
                .builder()
                .name(graphqlName(method))
                .method(method)
                .parameters(parameters)
                .build();
    }

    String name;
    Method method;
    List<GraphqlParameterMetadata> parameters;

}

@Getter
@ToString
@Builder(access = AccessLevel.PACKAGE)
class GraphqlParameterMetadata {

    public static GraphqlParameterMetadata of(Parameter parameter) {
        GraphqlParam ann = AnnotationUtils.getAnnotation(parameter, GraphqlParam.class);
        return GraphqlParameterMetadata
                .builder()
                .name(graphqlName(parameter))
                .parameter(parameter)
                .condition(ann == null ? Condition.EQUAL : ann.condition())
                .build();
    }

    String name;
    Parameter parameter;
    Condition condition;

}
