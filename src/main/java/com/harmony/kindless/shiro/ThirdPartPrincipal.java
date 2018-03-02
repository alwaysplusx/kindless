package com.harmony.kindless.shiro;

import java.io.Serializable;

/**
 * @author wuxii@foxmail.com
 */
public class ThirdPartPrincipal implements Serializable {

    private static final long serialVersionUID = 2109042544919744597L;
    private String username;
    private String client;
    private String scope;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

}
