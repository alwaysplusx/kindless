package com.kindless.core.auditor.reactive;

import com.kindless.core.auditor.Auditor;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @author wuxin
 */
public interface WebAuditorExtractor {

    Auditor extract(ServerHttpRequest request);

}
