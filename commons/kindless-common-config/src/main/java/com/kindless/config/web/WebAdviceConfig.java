package com.kindless.config.web;

import com.kindless.core.WebResponse;
import com.kindless.core.web.WebErrorHandler;
import com.kindless.core.web.WebErrorResponse;
import com.kindless.core.web.error.GlobalWebErrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * @author wuxin
 */
@Slf4j
@Configuration
public class WebAdviceConfig {

    @Value("${kindless.web.error.default.http-status:200}")
    private int httpStatus;

    @Value("${kindless.web.error.default.code:-1}")
    private int code;

    @Value("${kindless.web.error.default.message:unknown_error}")
    private String message;

    @Bean
    public GlobalControllerAdvice globalControllerAdvice() {
        return new GlobalControllerAdvice(webErrorHandler());
    }

    @Bean
    public WebErrorHandler webErrorHandler() {
        return GlobalWebErrorHandler
                .builder()
                .setDefaultErrorCode(code)
                .setDefaultErrorMessage(message)
                .setDefaultHttpStatus(httpStatus)
                .build();
    }

    @ControllerAdvice
    public static class GlobalControllerAdvice {

        private final WebErrorHandler webErrorHandler;

        public GlobalControllerAdvice(WebErrorHandler webErrorHandler) {
            this.webErrorHandler = webErrorHandler;
        }

        @ResponseBody
        @ExceptionHandler(Throwable.class)
        public WebResponse<Object> handleException(Throwable error, NativeWebRequest webRequest) {
            WebErrorResponse errorResponse = webErrorHandler.handle(error, webRequest);
            HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
            if (response != null) {
                response.setStatus(errorResponse.getHttpStatus());
            } else {
                log.warn("HttpServletResponse not valid in NativeWebRequest");
            }
            return errorResponse.getBody();
        }

    }

}
