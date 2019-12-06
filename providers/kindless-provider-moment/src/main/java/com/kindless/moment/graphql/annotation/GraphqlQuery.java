package com.kindless.moment.graphql.annotation;

import org.springframework.core.annotation.AliasFor;

/**
 * 仅适用单表查?
 *
 * @author wuxin
 */
public @interface GraphqlQuery {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

}
