package com.harmony.kindless.shiro;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

/**
 * Json Web Token(jwt)
 * 
 * @author wuxii@foxmail.com
 */
public interface JwtToken {

    /**
     * token原文本
     * 
     * @return token原文本
     */
    String getToken();

    /**
     * 此token所代表的用户
     * 
     * @return 用户名
     */
    String getUsername();

    /**
     * 验证token是否超时
     * 
     * @return true已超时, false未超时
     */
    boolean isExpired();

    /**
     * 第三方账号, 如应用支持oauth2.0授权可以为第三方授权. 则此部分标识第三方的信息
     * 
     * @return 第三方账号
     */
    ThridpartPrincipal getThridpartPrincipal();

    /**
     * 当前token的请求来源, 一般通过request来生成来源信息(ip/host)
     * 
     * @return 请求来源
     */
    OriginClaims getOriginClaims();

    /**
     * 第三方账号主要信息
     * 
     * @author wuxii@foxmail.com
     */
    public class ThridpartPrincipal implements Serializable {

        private static final long serialVersionUID = -5127300898708461951L;

        private final String username;
        private final String client;
        private final String scope;

        public ThridpartPrincipal(String username, String client, String scope) {
            this.username = username;
            this.client = client;
            this.scope = scope;
        }

        public String getUsername() {
            return username;
        }

        /**
         * 第三方账号
         * 
         * @return 第三方client
         */
        public String getClient() {
            return client;
        }

        /**
         * 第三方授权的scope
         * 
         * @return 授予第三方的scope
         */
        public String getScope() {
            return scope;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((client == null) ? 0 : client.hashCode());
            result = prime * result + ((scope == null) ? 0 : scope.hashCode());
            result = prime * result + ((username == null) ? 0 : username.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ThridpartPrincipal other = (ThridpartPrincipal) obj;
            if (client == null) {
                if (other.client != null)
                    return false;
            } else if (!client.equals(other.client))
                return false;
            if (scope == null) {
                if (other.scope != null)
                    return false;
            } else if (!scope.equals(other.scope))
                return false;
            if (username == null) {
                if (other.username != null)
                    return false;
            } else if (!username.equals(other.username))
                return false;
            return true;
        }

    }

    public static OriginClaims createOriginClaims(HttpServletRequest request) {
        return new OriginClaims(request);
    }

    public class OriginClaims implements Serializable {

        private static final long serialVersionUID = 6298919401887560234L;
        private transient HttpServletRequest request;
        private String device;
        private String host;

        public OriginClaims(HttpServletRequest request) {
            this.setRequest(request);
        }

        /**
         * 来源的设备
         * 
         * @return 设备名称
         */
        public String getDevice() {
            return device;
        }

        /**
         * 来源的host
         * 
         * @return request host
         */
        public String getHost() {
            return host;
        }

        /**
         * 如果是http请求则返回http request, 否则返回null
         * 
         * @return http request
         */
        public HttpServletRequest getHttpRequest() {
            return request;
        }

        /**
         * 设置http请求
         * 
         * @param request
         */
        public void setRequest(HttpServletRequest request) {
            this.request = request;
            this.device = request.getHeader("User-Agent");
            this.host = request.getRemoteAddr();
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((device == null) ? 0 : device.hashCode());
            result = prime * result + ((host == null) ? 0 : host.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            OriginClaims other = (OriginClaims) obj;
            if (device == null) {
                if (other.device != null)
                    return false;
            } else if (!device.equals(other.device))
                return false;
            if (host == null) {
                if (other.host != null)
                    return false;
            } else if (!host.equals(other.host))
                return false;
            return true;
        }

    }

}
