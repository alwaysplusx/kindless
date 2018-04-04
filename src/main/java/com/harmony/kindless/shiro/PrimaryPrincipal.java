package com.harmony.kindless.shiro;

/**
 * @author wuxii@foxmail.com
 */
public interface PrimaryPrincipal {

    Long getUserId();

    String getUsername();

    String getNickname();

    String getClientId();

    String getToken();

    Object getParam(String name);

}
