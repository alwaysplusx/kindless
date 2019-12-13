package com.kindless.moment.graphql.annotation.condition;

import com.kindless.moment.graphql.annotation.Condition;
import com.kindless.moment.graphql.annotation.GraphqlParam;
import org.springframework.core.annotation.AliasFor;

/**
 * @author wuxin
 */
@GraphqlParam(Condition.LIKE)
public @interface Like {

    @AliasFor(annotation = GraphqlParam.class, attribute = "name")
    String value() default "";

}
