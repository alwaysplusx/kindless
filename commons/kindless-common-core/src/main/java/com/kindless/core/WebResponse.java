package com.kindless.core;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wuxin
 */
@Data
@Accessors(chain = true)
public class WebResponse<T> implements CodeResponse {

    public static <T> WebResponse<T> ok(T data) {
        return new WebResponse<>(data);
    }

    public static <T> WebResponse<T> failed(String msg) {
        return new WebResponse<>(ERROR, msg);
    }

    public static <T> WebResponse<T> failed(int code, String msg) {
        return new WebResponse<>(code, msg);
    }

    private String requestId;
    private String path;
    private long timestamp = System.currentTimeMillis();

    private int code;
    private String message;
    private T data;

    public WebResponse() {
    }

    public WebResponse(int code, String msg) {
        this.code = code;
        this.message = msg;
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
