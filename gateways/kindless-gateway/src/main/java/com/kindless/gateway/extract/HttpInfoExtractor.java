package com.kindless.gateway.extract;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

/**
 * @author wuxin
 */
public interface HttpInfoExtractor {

    RequestHttpInfo extractRequestHttpInfo(ServerHttpRequest request);

    ResponseHttpInfo extractResponseHttpInfo(ServerHttpResponse response);

}
