package com.harmony.kindless.core.handler;

import com.harmony.kindless.apis.CodeException;
import com.harmony.umbrella.web.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author wuxii
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CodeException.class)
	public Response handleCodeException(CodeException failed) {
		return Response.error(failed.getCode(), failed.getMessage());
	}

}
