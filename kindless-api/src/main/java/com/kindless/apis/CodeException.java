package com.kindless.apis;

import com.harmony.umbrella.web.ResponseException;

/**
 * @author wuxii
 */
public class CodeException extends ResponseException {

    private static final long serialVersionUID = -943166406382058793L;

    public static CodeException exists(String msg) {
        return new CodeException(Responses.EXISTS, msg);
    }

    public static CodeException notFound(String msg) {
        return new CodeException(Responses.NOT_EXISTS, msg);
    }

    public static CodeException error(String msg, Exception e) {
        return new CodeException(Responses.ERROR, msg, e);
    }

    public CodeException(Responses code) {
        this(code.code(), code.message());
    }

    public CodeException(Responses code, String msg) {
        this(code.code(), msg);
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

    public CodeException(Responses code, String msg, Exception cause) {
        this(code.code(), msg, cause);
    }

}
