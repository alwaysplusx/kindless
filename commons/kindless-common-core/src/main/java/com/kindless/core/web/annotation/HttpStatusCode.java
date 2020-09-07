package com.kindless.core.web.annotation;

import java.lang.annotation.*;

/**
 * @author wuxin
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpStatusCode {

    int value() default 200;

}
