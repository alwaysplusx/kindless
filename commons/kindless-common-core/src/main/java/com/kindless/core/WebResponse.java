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

    public static <T> WebResponse<T> failed(String msg) {
        return new WebResponse<>(ERROR, msg);
    }

    private int code;
    private String msg;
    private T data;

    public WebResponse() {
    }

    public WebResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public WebResponse(T data) {
        this.code = OK;
        this.data = data;
    }

    @Override
    public int getCode() {
        return code;
    }

}
