package com.harmony.kindless.core.util;

import com.harmony.kindless.core.Responses;
import com.harmony.umbrella.web.Response;
import com.harmony.umbrella.web.ResponseDetails;
import com.harmony.umbrella.web.ResponseException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author wuxii
 */
public class ResponseUtils {

    public static <T> Response<T> fallback(Throwable failed) {
        return fallback(failed, ResponseUtils::getExceptionResponseDetails);
    }

    public static <T> Response<T> fallback(Throwable failed, Responses def) {
        return fallback(failed, e -> def);
    }

    private static <T> Response<T> fallback(Throwable failed, Function<Throwable, Responses> fun) {
        if (failed instanceof ResponseException) {
            return Response.error((ResponseException) failed);
        }
        if (failed instanceof ResponseDetails) {
            return Response.of((ResponseDetails) failed);
        }
        return Response.of(fun.apply(failed));
    }

    public static Responses getExceptionResponseDetails(Throwable failed) {
        if (failed == null) {
            return null;
        }
        Responses code = RESPONSE_DETAILS.get(failed.getClass().getName());
        return code != null ? code : getExceptionResponseDetails(failed.getCause());
    }

    private static final Map<Object, Responses> RESPONSE_DETAILS = new HashMap<>();

    static {
        RESPONSE_DETAILS.put("com.netflix.hystrix.exception.HystrixTimeoutException", Responses.TIMEOUT);
    }

}
