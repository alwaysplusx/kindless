package com.harmony.kindless.apis;

/**
 * @author wuxii
 */
public class CodeException extends RuntimeException {

    public static CodeException exists(String msg) {
        return new CodeException(ResponseCodes.EXISTS, msg);
    }

    public static CodeException notFound(String msg) {
        return new CodeException(ResponseCodes.NOT_FOUND, msg);
    }

    public static CodeException error(String msg, Exception e) {
        return new CodeException(ResponseCodes.ERROR, msg, e);
    }

    private final int code;

    public CodeException(ResponseCodes code) {
        this(code.code(), code.message());
    }

    public CodeException(ResponseCodes code, String msg) {
        this(code.code(), msg);
    }

    public CodeException(int code) {
        super();
        this.code = code;
    }

    public CodeException(int code, String message) {
        this(code, message, null);
    }

    public CodeException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public CodeException(ResponseCodes code, String msg, Exception cause) {
        this(code.code(), msg, cause);
    }

    public int getCode() {
        return code;
    }

}
