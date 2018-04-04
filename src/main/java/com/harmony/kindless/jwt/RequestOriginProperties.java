package com.harmony.kindless.jwt;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuxii@foxmail.com
 */
public class RequestOriginProperties implements Serializable {

    private static final long serialVersionUID = -2873090738787328094L;

    private transient HttpServletRequest httpRequest;
    private String device;
    private String host;

    public RequestOriginProperties() {
    }

    public RequestOriginProperties(HttpServletRequest httpRequest) {
        this.setHttpRequest(httpRequest);
    }

    public RequestOriginProperties(String device, String host) {
        this.device = device;
        this.host = host;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setHttpRequest(HttpServletRequest httpRequest) {
        this.httpRequest = httpRequest;
        this.device = httpRequest.getHeader("User-Agent");
        this.host = httpRequest.getRemoteHost();
    }

    public HttpServletRequest getHttpRequest() {
        return httpRequest;
    }
}