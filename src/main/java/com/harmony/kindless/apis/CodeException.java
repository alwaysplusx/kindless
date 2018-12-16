package com.harmony.kindless.apis;

import java.util.function.Supplier;

/**
 * @author wuxii
 */
public class CodeException extends RuntimeException {

    public static Supplier<CodeException> notFound(String msg) {
        return () -> new CodeException(msg);
    }

    public CodeException() {
        super();
    }

    public CodeException(String message) {
        super(message);
    }

    public CodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CodeException(Throwable cause) {
        super(cause);
    }

}
