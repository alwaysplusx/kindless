package com.kindless.gateway.extract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.AbstractServerHttpRequest;
import org.springframework.http.server.reactive.AbstractServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;

import java.net.InetSocketAddress;

/**
 * @author wuxin
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimpleHttpInfoExtractor implements HttpInfoExtractor {

    @Builder.Default
    private int maxStack = 10;

    @Override
    public RequestHttpInfo extractRequestHttpInfo(ServerHttpRequest request) {
        return applyRequestHttpInfo(request, new RequestHttpInfo(), 0);
    }

    @Override
    public ResponseHttpInfo extractResponseHttpInfo(ServerHttpResponse response) {
        return applyResponseHttpInfo(response, new ResponseHttpInfo(), 0);
    }

    protected RequestHttpInfo applyRequestHttpInfo(ServerHttpRequest request, RequestHttpInfo info, int currentStack) {
        if (info.getRemoteAddress() == null) {
            info.setRemoteAddress(getAddress(request.getRemoteAddress()));
        }
        if (info.getLocalAddress() == null && request.getLocalAddress() != null) {
            info.setLocalAddress(getAddress(request.getLocalAddress()));
        }
        if (info.getPath() == null) {
            info.setPath(request.getPath().value());
        }
        request.getQueryParams();
        if (info.getMethod() == null) {
            info.setMethod(request.getMethodValue());
        }
        if (info.getHeaders() == null) {
            info.setHeaders(request.getHeaders());
        }
        if (info.getQueryParams() == null) {
            info.setQueryParams(request.getQueryParams());
        }
        if (info.isDone() || currentStack > maxStack) {
            return info;
        }
        if (request instanceof AbstractServerHttpRequest) {
            Object nativeRequest = ((AbstractServerHttpRequest) request).getNativeRequest();
            if (nativeRequest instanceof HttpServerRequest) {
                applyHttpServerRequestInfo((HttpServerRequest) nativeRequest, info);
            }
            if (nativeRequest instanceof ServerHttpRequest) {
                return applyRequestHttpInfo((ServerHttpRequest) nativeRequest, info, currentStack + 1);
            }
        }
        return info;
    }

    private void applyHttpServerRequestInfo(HttpServerRequest request, RequestHttpInfo info) {
        if (info.getVersion() == null) {
            info.setVersion(request.version().text());
        }
    }

    protected ResponseHttpInfo applyResponseHttpInfo(ServerHttpResponse response, ResponseHttpInfo info, int currentStack) {
        if (info.getHeaders() == null) {
            info.setHeaders(response.getHeaders());
        }
        if (info.isDone() || currentStack > maxStack) {
            return info;
        }
        if (response instanceof AbstractServerHttpResponse) {
            applyServerHttpResponseInfo((AbstractServerHttpResponse) response, info);
            Object nativeResponse = ((AbstractServerHttpResponse) response).getNativeResponse();
            if (nativeResponse instanceof HttpServerResponse) {
                applyHttpServerResponseInfo((HttpServerResponse) nativeResponse, info);
            }
            if (nativeResponse instanceof ServerHttpResponse) {
                return applyResponseHttpInfo((ServerHttpResponse) nativeResponse, info, currentStack + 1);
            }
        }
        return info;
    }

    private void applyServerHttpResponseInfo(AbstractServerHttpResponse response, ResponseHttpInfo info) {
        HttpStatus statusCode = response.getStatusCode();
        if (statusCode != null) {
            if (info.getReason() == null) {
                info.setReason(statusCode.getReasonPhrase());
            }
            if (info.getStatusCode() == null) {
                info.setStatusCode(statusCode.value() + "");
            }
        }
    }

    private void applyHttpServerResponseInfo(HttpServerResponse response, ResponseHttpInfo info) {
        if (info.getVersion() == null) {
            info.setVersion(response.version().text());
        }
        if (info.getReason() == null) {
            info.setReason(response.status().reasonPhrase());
        }
        if (info.getStatusCode() == null) {
            info.setStatusCode(response.status().codeAsText().toString());
        }
    }

    private String getAddress(InetSocketAddress address) {
        return address == null ? null : address.getHostName() + ":" + address.getPort();
    }

}
