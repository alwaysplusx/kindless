package com.kindless.core.web.error;

import com.kindless.core.CodeResponse;
import com.kindless.core.WebRequestException;
import com.kindless.core.WebResponse;
import com.kindless.core.web.WebErrorResponse;
import com.kindless.core.web.annotation.HttpStatusCode;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * @author wuxin
 */
@Builder(setterPrefix = "set")
public class GlobalWebErrorHandler implements com.kindless.core.web.WebErrorHandler {

    @Builder.Default
    private int defaultHttpStatus = HttpStatus.OK.value();
    @Builder.Default
    private int defaultErrorCode = CodeResponse.ERROR;
    @Builder.Default
    private String defaultErrorMessage = "unknown_error";

    @Override
    public WebErrorResponse handle(Throwable error, NativeWebRequest webRequest) {
        WebRequestException wre = extractWebRequestException(error);
        if (wre != null) {
            return handleWebRequestError(wre, webRequest);
        }
        return handleUnknownError(error, webRequest);
    }

    private WebErrorResponse handleUnknownError(Throwable error, NativeWebRequest webRequest) {
        int httpStatus = getErrorHttpStatus(error);
        return new WebErrorResponse()
                .setBody(WebResponse.failed(defaultErrorCode, defaultErrorMessage))
                .setHttpStatus(httpStatus);
    }

    protected WebErrorResponse handleWebRequestError(WebRequestException error, NativeWebRequest webRequest) {
        String message = error.getMessage();
        WebResponse<Object> webResp = WebResponse.failed(error.getCode(), message);
        int httpStatus = getErrorHttpStatus(error);
        return new WebErrorResponse()
                .setBody(webResp)
                .setHttpStatus(httpStatus);
    }

    protected WebRequestException extractWebRequestException(Throwable error) {
        if (error instanceof WebRequestException) {
            return (WebRequestException) error;
        }
        if (error instanceof CodeResponse) {
            int code = ((CodeResponse) error).getCode();
            String message = error.getMessage();
            WebRequestException wre = new WebRequestException(code, message);
            wre.setHttpStatus(getErrorHttpStatus(error));
            return wre;
        }
        return null;
    }

    private int getErrorHttpStatus(Throwable error) {
        int httpStatus = defaultHttpStatus;
        HttpStatusCode ann = error.getClass().getAnnotation(HttpStatusCode.class);
        if (ann != null) {
            httpStatus = ann.value();
        }
        return httpStatus;
    }

}
