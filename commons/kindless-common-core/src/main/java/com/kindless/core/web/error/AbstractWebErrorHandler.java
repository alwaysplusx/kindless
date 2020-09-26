package com.kindless.core.web.error;

import com.kindless.core.CodeResponse;
import com.kindless.core.WebRequestException;
import com.kindless.core.WebResponse;
import com.kindless.core.web.WebErrorResponse;
import com.kindless.core.web.annotation.HttpStatusCode;
import org.springframework.http.HttpStatus;

/**
 * @author wuxin
 */
public abstract class AbstractWebErrorHandler<T> {

    protected int defaultHttpStatus;

    protected int defaultErrorCode;

    protected String defaultErrorMessage;

    public AbstractWebErrorHandler() {
        this(HttpStatus.OK.value(), CodeResponse.ERROR, "unknown_error");
    }

    public AbstractWebErrorHandler(int defaultHttpStatus, int defaultErrorCode, String defaultErrorMessage) {
        this.defaultErrorCode = defaultErrorCode;
        this.defaultErrorMessage = defaultErrorMessage;
        this.defaultHttpStatus = defaultHttpStatus;
    }

    protected WebErrorResponse handleUnknownError(Throwable error, T webRequest) {
        int httpStatus = getErrorHttpStatus(error);
        return new WebErrorResponse()
                .setBody(WebResponse.failed(defaultErrorCode, defaultErrorMessage))
                .setHttpStatus(httpStatus);
    }

    protected WebErrorResponse handleWebRequestError(WebRequestException error, T webRequest) {
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

    public void setDefaultErrorCode(int defaultErrorCode) {
        this.defaultErrorCode = defaultErrorCode;
    }

    public void setDefaultErrorMessage(String defaultErrorMessage) {
        this.defaultErrorMessage = defaultErrorMessage;
    }

    public void setDefaultHttpStatus(int defaultHttpStatus) {
        this.defaultHttpStatus = defaultHttpStatus;
    }

}
