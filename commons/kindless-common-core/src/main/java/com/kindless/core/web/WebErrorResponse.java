package com.kindless.core.web;

import com.kindless.core.WebResponse;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author wuxin
 */
@Data
@Accessors(chain = true)
public class WebErrorResponse {

    private WebResponse<Object> body;
    private int httpStatus;
    private Map<String, String> headers;

}
