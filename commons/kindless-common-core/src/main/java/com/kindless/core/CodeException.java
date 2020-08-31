package com.kindless.core;

/**
 * @author wuxii
 */
public class CodeException extends RuntimeException implements CodeResponse {

    private static final long serialVersionUID = -943166406382058793L;
    private final int code;

    public CodeException(int code, String message) {
        super(message);
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }

}
