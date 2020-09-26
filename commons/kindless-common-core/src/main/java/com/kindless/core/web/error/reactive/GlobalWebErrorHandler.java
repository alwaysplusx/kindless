package com.kindless.core.web.error.reactive;

import com.kindless.core.WebRequestException;
import com.kindless.core.web.WebErrorResponse;
import com.kindless.core.web.error.AbstractWebErrorHandler;
import com.kindless.core.web.reactive.WebErrorHandler;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author wuxin
 */
@Slf4j
public class GlobalWebErrorHandler extends AbstractWebErrorHandler<ServerWebExchange> implements WebErrorHandler {

    public GlobalWebErrorHandler() {
    }

    @Builder(setterPrefix = "set")
    public GlobalWebErrorHandler(int defaultHttpStatus, int defaultErrorCode, String defaultErrorMessage) {
        super(defaultHttpStatus, defaultErrorCode, defaultErrorMessage);
    }

    @Override
    public WebErrorResponse handle(Throwable error, ServerWebExchange exchange) {
        WebRequestException wre = extractWebRequestException(error);
        if (wre != null) {
            return handleWebRequestError(wre, exchange);
        }
        return handleUnknownError(error, exchange);
    }

}
