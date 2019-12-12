package com.kindless.moment.graphql.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface GraphqlField {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

}
