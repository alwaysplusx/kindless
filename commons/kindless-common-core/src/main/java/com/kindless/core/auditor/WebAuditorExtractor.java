package com.kindless.core.auditor;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuxin
 */
public interface WebAuditorExtractor {

    Auditor extract(HttpServletRequest request);

}
