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
@Target({ElementType.FIELD})
@GraphqlParam(Condition.NOT_LIKE)
public @interface NotLike {

    @AliasFor(annotation = GraphqlParam.class, attribute = "name")
    String value() default "";

}
