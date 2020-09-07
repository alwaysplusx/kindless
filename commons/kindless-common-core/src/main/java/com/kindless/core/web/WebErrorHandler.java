package com.kindless.core.web;

import org.springframework.web.context.request.NativeWebRequest;

/**
 * @author wuxin
 */
public interface WebErrorHandler {

    WebErrorResponse handle(Throwable error, NativeWebRequest webRequest);

}
