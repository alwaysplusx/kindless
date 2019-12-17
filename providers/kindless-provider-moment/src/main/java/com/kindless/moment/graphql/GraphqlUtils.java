package com.kindless.moment.graphql;

import com.harmony.umbrella.util.StringUtils;
import com.kindless.moment.graphql.annotation.*;
import graphql.language.FieldDefinition;
import graphql.language.InputValueDefinition;
import graphql.language.ObjectTypeDefinition;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;


/**
 * @author wuxin
 */
public class GraphqlUtils {

    static String graphqlName(AnnotatedElement annotatedElement) {
        if (annotatedElement instanceof Class) {
            return graphqlObjectName((Class<?>) annotatedElement);
        } else if (annotatedElement instanceof Method) {
            return graphqlQueryName((Method) annotatedElement);
        } else if (annotatedElement instanceof Parameter) {
            return graphqlParamName((Parameter) annotatedElement);
        } else if (annotatedElement instanceof Field) {
            return graphqlFieldName((Field) annotatedElement);
        }
        throw new IllegalArgumentException("unsupported type " + annotatedElement);
    }

    static String graphqlQueryName(Method method) {
        GraphqlQuery ann = AnnotationUtils.getAnnotation(method, GraphqlQuery.class);
        return StringUtils.getFirstNotBlank(ann != null ? ann.name() : null, method.getName());
    }

    public static String graphqlQueryName(Class<?> type) {
        GraphqlQuery ann = AnnotationUtils.getAnnotation(type, GraphqlQuery.class);
        return StringUtils.getFirstNotBlank(ann != null ? ann.name() : null, type.getSimpleName());
    }

    private static String graphqlFieldName(Field field) {
        GraphqlField ann = AnnotationUtils.getAnnotation(field, GraphqlField.class);
        return StringUtils.getFirstNotBlank(ann != null ? ann.name() : null, field.getName());
    }

    private static String graphqlObjectName(Class<?> type) {
        GraphqlObject ann = AnnotationUtils.getAnnotation(type, GraphqlObject.class);
        return StringUtils.getFirstNotBlank(ann != null ? ann.name() : null, type.getSimpleName());
    }

    private static String graphqlParamName(Parameter parameter) {
        GraphqlParam ann = AnnotationUtils.getAnnotation(parameter, GraphqlParam.class);
        return StringUtils.getFirstNotBlank(ann != null ? ann.name() : null, parameter.getName());
    }

    static boolean hasGraphqlIgnoreAnnotation(AnnotatedElement annotatedElement) {
        return AnnotationUtils.getAnnotation(annotatedElement, GraphqlIgnore.class) != null;
    }

    static boolean hasGraphqlQueryAnnotation(AnnotatedElement annotatedElement) {
        return AnnotationUtils.getAnnotation(annotatedElement, GraphqlQuery.class) != null;
    }

    static FieldDefinition fieldDefinition(GraphqlFieldMetadata fieldMetadata) {
        return null;
    }

    static FieldDefinition methodDefinition(GraphqlMethodMetadata methodMetadata) {
        return null;
    }

    static ObjectTypeDefinition objectDefinition(GraphqlMetadata objectMetadata) {
        return null;
    }

    static InputValueDefinition parameterDefinition(GraphqlParameterMetadata parameterMetadata) {
        return null;
    }

}
