package com.kindless.moment.graphql.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author wuxin
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface GraphqlParam {

    @AliasFor("condition")
    Condition value() default Condition.EQUAL;

    @AliasFor("value")
    Condition condition() default Condition.EQUAL;

    String name() default "";

}
