package com.harmony.kindless.core;

import com.harmony.umbrella.web.ResponseDetails;
import com.harmony.umbrella.web.ResponseException;

/**
 * @author wuxii
 */
public class CodeException extends ResponseException {

    private static final long serialVersionUID = -943166406382058793L;

    public CodeException(ResponseDetails rd) {
        this(rd.getCode(), rd.getMsg());
    }

    public CodeException(ResponseDetails rd, String msg) {
        this(rd.getCode(), msg);
    }

    public CodeException(int code) {
        super(code);
    }

    public CodeException(int code, String message) {
        this(code, message, null);
    }

    public CodeException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public CodeException(ResponseDetails rd, String msg, Exception cause) {
        this(rd.getCode(), msg, cause);
    }

}
