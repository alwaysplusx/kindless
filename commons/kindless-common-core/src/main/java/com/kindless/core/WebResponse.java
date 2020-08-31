package com.kindless.core;

/**
 * @author wuxin
 */
public class WebResponse<T> implements CodeResponse {

    public static <T> WebResponse<T> ok(T data) {
        return new WebResponse<>();
    }

    private int code;
    private T data;

    @Override
    public int getCode() {
        return code;
    }

}
