package com.harmony.kindless.core.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.harmony.umbrella.data.domain.BaseEntity;

/**
 * @author wuxii@foxmail.com
 */
@Entity
@Table(name = "K_WEB_TOKEN")
public class WebToken extends BaseEntity<String> {

    private static final long serialVersionUID = -6426816488499627125L;

    @Id
    private String webToken;
    private String username;
    private String secretKey;

    @Override
    public String getId() {
        return webToken;
    }

    public String getWebToken() {
        return webToken;
    }

    public void setWebToken(String webToken) {
        this.webToken = webToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

}
