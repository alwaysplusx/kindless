package com.kindless.core;

import lombok.Data;

/**
 * @author wuxin
 */
@Data
public class WebResponse<T> implements CodeResponse {

    public static <T> WebResponse<T> ok(T data) {
        return new WebResponse<>(data);
    }

    private int code;
    private T data;

    public WebResponse() {
    }

    public WebResponse(T data) {
        this.data = data;
    }

    @Override
    public int getCode() {
        return code;
    }

}
