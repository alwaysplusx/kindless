package com.kindless.moment.graphql.annotation;

import java.lang.annotation.*;

/**
 * @author wuxin
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface GraphqlParam {
}
