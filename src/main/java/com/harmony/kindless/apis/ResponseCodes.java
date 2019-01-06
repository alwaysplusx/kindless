package com.harmony.kindless.apis;

import com.harmony.umbrella.web.Response;

/**
 * @author wuxii
 */
public enum ResponseCodes {

    ERROR(-1, "系统繁忙"),
    OK(0, "请求成功"),
    UNAUTHORIZATION(40001, "unauthorized");

    private int code;
    private String msg;

    ResponseCodes(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response<?> toResponse() {
        return Response.error(code, msg);
    }

}
