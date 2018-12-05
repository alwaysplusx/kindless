package com.harmony.kindless.apis.domain.weixin;

import com.harmony.kindless.apis.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author wuxii
 */
@Entity
@Table
public class WeixinMpConfig extends BaseEntity {

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
