package com.kindless.moment.graphql;

import com.harmony.umbrella.util.StringUtils;
import com.kindless.moment.graphql.annotation.GraphqlField;
import com.kindless.moment.graphql.annotation.GraphqlType;
import graphql.Scalars;
import graphql.language.Description;
import graphql.language.FieldDefinition;
import graphql.language.ObjectTypeDefinition;
import graphql.language.SourceLocation;
import graphql.schema.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class GraphqlBuilder {

    /**
     * 构建GraphqlType, 对于于:
     * <pre>
     *  type User {
     *      userId: Int
     *      username: String
     *  }
     * </pre>
     *
     * @param type
     * @return
     */
    public static GraphQLObjectType graphqlObject(Class<?> type) {
        if (isPrimitiveType(type)) {
            throw new IllegalArgumentException("primitive type class " + type);
        }
        GraphQLObjectType.Builder builder = GraphQLObjectType
                .newObject()
                .name(type.getSimpleName())
                .description(graphTypeName(type))
                .definition(typeDefinition(type));


        for (Field field : typeFields(type)) {
            builder.field(graphqlObjectField(field));
        }
        return builder.build();
    }

    /**
     * 构建type中的字段
     * <pre>
     *  type User {
     *      userId: Int # √
     *      username: String
     *  }
     * </pre>
     *
     * @param field
     * @return
     */
    protected static GraphQLFieldDefinition graphqlObjectField(Field field) {
        String graphFieldName = graphFieldName(field);
        return GraphQLFieldDefinition
                .newFieldDefinition()
                .name(graphFieldName)
                .type(graphqlFieldType(field))
                .description(graphFieldName)
                .definition(fieldDefinition(field))
                .build();
    }

    static GraphQLOutputType graphqlFieldType(Field field) {
        Class<?> actualType = getFieldActualType(field);
        GraphQLOutputType graphqlType = isPrimitiveType(actualType) ? graphPrimitiveType(actualType) : graphqlObject(actualType);
        return field.getType().isArray() || Collection.class.isAssignableFrom(field.getType())
                ? GraphQLList.list(graphqlType)
                : graphqlType;
    }

    static ObjectTypeDefinition typeDefinition(Class<?> type) {
        ObjectTypeDefinition definition = new ObjectTypeDefinition(type.getSimpleName());
        definition.setDescription(typeDescription(type));
        definition.setSourceLocation(typeSourceLocation(type));
        List<FieldDefinition> fieldDefinitions = definition.getFieldDefinitions();
        for (Field field : typeFields(type)) {
            fieldDefinitions.add(fieldDefinition(field));
        }
        return definition;
    }

    static FieldDefinition fieldDefinition(Field field) {
        FieldDefinition fieldDefinition = new FieldDefinition(field.getName());
        fieldDefinition.setDescription(fieldDescription(field));
        // fieldDefinition.setType();
        fieldDefinition.setSourceLocation(fieldSourceLocation(field));
        return fieldDefinition;
    }

    static Description fieldDescription(Field field) {
        return new Description(field.getName(), fieldSourceLocation(field), false);
    }

    static Description typeDescription(Class<?> type) {
        return new Description(type.getSimpleName(), typeSourceLocation(type), false);
    }

    static SourceLocation fieldSourceLocation(Field field) {
        return new SourceLocation(-1, -1, field.getDeclaringClass().getName() + "." + field.getName());
    }

    static SourceLocation typeSourceLocation(Class<?> type) {
        return new SourceLocation(-1, -1, type.getName());
    }

    static String graphFieldName(Field field) {
        GraphqlField ann = AnnotationUtils.getAnnotation(field, GraphqlField.class);
        return StringUtils.getFirstNotBlank(ann != null ? ann.name() : null, field.getName());
    }

    static String graphTypeName(Class<?> type) {
        GraphqlType ann = AnnotationUtils.getAnnotation(type, GraphqlType.class);
        return StringUtils.getFirstNotBlank(ann != null ? ann.name() : null, type.getSimpleName());
    }

    static List<Field> typeFields(Class<?> type) {
        return Stream.of(type.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .collect(Collectors.toList());
    }

    static GraphQLScalarType graphPrimitiveType(Class<?> type) {
        return PRIMITIVE_TYPES.get(type);
    }

    static boolean isArrayOrCollection(Class<?> type) {
        return type.isArray() || Collection.class.isAssignableFrom(type);
    }

    static boolean isPrimitiveType(Class<?> type) {
        return PRIMITIVE_TYPES.containsKey(type) || Object.class == type;
    }

    static boolean isPrimitiveArrayOrCollectionField(Field field) {
        return isArrayOrCollection(field.getType()) && isPrimitiveType(getFieldActualType(field));
    }

    static Class<?> getFieldActualType(Field field) {
        Class<?> fieldType = field.getType();
        if (fieldType.isArray()) {
            return fieldType.getComponentType();
        }
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) genericType).getActualTypeArguments()[0];
        }
        return fieldType;
    }

    static final Map<Class, GraphQLScalarType> PRIMITIVE_TYPES = new HashMap<>();

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

}
