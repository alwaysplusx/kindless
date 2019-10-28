package com.kindless.apis.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuxii
 */
public class RequestUtils {

    public static String getRequestUri(HttpServletRequest request) {
        return request.getRequestURI();
    }

}
