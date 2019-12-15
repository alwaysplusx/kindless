package com.kindless.moment.graphql.annotation.condition;

import com.kindless.moment.graphql.annotation.Condition;
import com.kindless.moment.graphql.annotation.GraphqlParam;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author wuxin
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@GraphqlParam(Condition.EQUAL)
public @interface Equal {

    @AliasFor(annotation = GraphqlParam.class, attribute = "name")
    String value() default "";

}
