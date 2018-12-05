package com.harmony.kindless.wechat.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wuxii
 */
@ConfigurationProperties(prefix = "weixin.mp")
public class WeixinMpProperties {

    private String appId;
    private String secret;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

}
