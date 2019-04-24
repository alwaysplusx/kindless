package com.harmony.kindless.apis;

import com.harmony.umbrella.web.Response;
import com.harmony.umbrella.web.ResponseDetails;

/**
 * @author wuxii
 */
public enum Responses implements ResponseDetails {

    ERROR(Response.ERROR, "系统繁忙"),
    OK(Response.OK, "请求成功"),

    UNAUTHORIZED(40010, "unauthorized"),
    AUTHORIZATION_EXPIRED(40011, "authorization expired"),
    TOKEN_INCORRECT(400012, "token incorrect"),

    EXISTS(40020, "data exists"),
    NOT_EXISTS(40021, "not found"),
    WARN_PARAM(40022, "warn param"),

    TIMEOUT(50004, "timeout");

    private int code;
    private String msg;

    Responses(int code, String msg) {
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

    public static <T> Response<T> notExists() {
        return Response.of(NOT_EXISTS);
    }

}
