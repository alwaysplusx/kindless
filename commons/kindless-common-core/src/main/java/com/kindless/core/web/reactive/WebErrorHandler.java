package com.kindless.core.web.reactive;

import com.kindless.core.web.WebErrorResponse;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author wuxin
 */
public interface WebErrorHandler {

    WebErrorResponse handle(Throwable error, ServerWebExchange exchange);

}
