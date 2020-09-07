package com.kindless.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * @author wuxii
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WebRequestException extends RuntimeException implements CodeResponse {

    private static final long serialVersionUID = -943166406382058793L;

    private int code;
    private int httpStatus = HttpStatus.OK.value();

    public WebRequestException() {
    }

    public WebRequestException(String message) {
        this(ERROR, message);
    }

    public WebRequestException(int code, String message) {
        super(message);
        this.code = code;
    }

    public WebRequestException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }

}
