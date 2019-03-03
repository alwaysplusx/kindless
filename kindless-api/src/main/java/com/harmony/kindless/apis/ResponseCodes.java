package com.harmony.kindless.apis;

import com.harmony.umbrella.web.Response;
import com.harmony.umbrella.web.ResponseDetails;

/**
 * @author wuxii
 */
public enum ResponseCodes implements ResponseDetails {

    ERROR(Response.ERROR, "系统繁忙"),
    OK(Response.OK, "请求成功"),
    UNAUTHORIZED(40001, "unauthorized"),
    EXISTS(40002, "data exists"),
    ILLEGAL_ARGUMENT(40003, "illegal argument"),
    NOT_FOUND(40004, "not found"),
    AUTHORIZATION_EXPIRED(40005, "authorization expired"),
    TOKEN_INCORRECT(40006, "token incorrect"),

    TIMEOUT(50004, "timeout");

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

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

}
