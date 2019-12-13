package com.kindless.moment.graphql.annotation.condition;

import com.kindless.moment.graphql.annotation.GraphqlParam;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

import static com.kindless.moment.graphql.annotation.Condition.NOT_EQUAL;

/**
 * @author wuxin
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@GraphqlParam(NOT_EQUAL)
public @interface NotEqual {

    @AliasFor(annotation = GraphqlParam.class, attribute = "name")
    String value() default "";

}
