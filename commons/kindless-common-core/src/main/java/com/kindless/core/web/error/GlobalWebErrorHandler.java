package com.kindless.core.web.error;

import com.kindless.core.WebRequestException;
import com.kindless.core.web.WebErrorHandler;
import com.kindless.core.web.WebErrorResponse;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * @author wuxin
 */
@Slf4j
public class GlobalWebErrorHandler extends AbstractWebErrorHandler<NativeWebRequest> implements WebErrorHandler {

    public GlobalWebErrorHandler() {
    }

    @Builder(setterPrefix = "set")
    public GlobalWebErrorHandler(int defaultHttpStatus, int defaultErrorCode, String defaultErrorMessage) {
        this.defaultErrorCode = defaultErrorCode;
        this.defaultErrorMessage = defaultErrorMessage;
        this.defaultHttpStatus = defaultHttpStatus;
    }

    @Override
    public WebErrorResponse handle(Throwable error, NativeWebRequest webRequest) {
        log.warn("something wrong happen: {}", error.getMessage());
        WebRequestException wre = extractWebRequestException(error);
        if (wre != null) {
            return handleWebRequestError(wre, webRequest);
        }
        return handleUnknownError(error, webRequest);
    }

}
