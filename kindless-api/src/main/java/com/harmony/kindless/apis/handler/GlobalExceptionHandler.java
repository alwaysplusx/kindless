package com.harmony.kindless.apis.handler;

import com.harmony.kindless.apis.util.RequestUtils;
import com.harmony.umbrella.web.Response;
import com.harmony.umbrella.web.ResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wuxii
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // TODO exception handler 配置化?

    @ResponseBody
    @ExceptionHandler(ResponseException.class)
    public Response<?> handleCodeException(ResponseException failed, HttpServletRequest request, HttpServletResponse response) {
        log.error("请求失败: {}", RequestUtils.getRequestUri(request), failed);
        return Response.error(failed.getCode(), failed.getMessage());
    }


}
