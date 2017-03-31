package com.harmony.kindless.web;

import java.util.LinkedHashMap;

/**
 * @author wuxii@foxmail.com
 */
public class Response extends LinkedHashMap<String, Object> {

    private static final long serialVersionUID = -2865836797606915331L;

    public static Response ok() {
        Response response = new Response();
        response.put("success", true);
        return response;
    }

    public static Response error(int code, String msg) {
        Response response = new Response();
        response.put("error", msg);
        response.put("error_code", code);
        return response;
    }

    public static class ErrorResponseBuilder {

    }

    public static class ResponseBuilder {

    }

}
