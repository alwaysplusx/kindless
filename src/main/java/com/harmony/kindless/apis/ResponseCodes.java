package com.harmony.kindless.apis;

import com.harmony.umbrella.web.Response;

/**
 * @author wuxii
 */
public enum ResponseCodes {

    ERROR(Response.ERROR, "系统繁忙"),
    OK(Response.OK, "请求成功"),
    UNAUTHORIZED(40001, "unauthorized"),
    EXISTS(40002, "exists"),
    NOT_FOUND(40004, "not found");

    private int code;
    private String msg;

    ResponseCodes(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response<?> toResponse() {
        return Response.error(code, msg);
    }

    public CodeException toException() {
        return new CodeException(code, msg);
    }

    public int code() {
        return code;
    }

    public String message() {
        return msg;
    }

}
