package com.harmony.kindless.oauth.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.harmony.umbrella.data.domain.BaseEntity;

/**
 * @author wuxii@foxmail.com
 */
@Entity
@Table(name = "K_SCOPE_CODE")
public class ScopeCode extends BaseEntity<String> {

    private static final long serialVersionUID = 7545634150299041555L;

    @Id
    private String code;
    private String clientId;
    /**
     * 用户选择的权限范围
     */
    private String scopes;
    /**
     * 授权的用户(当前登录的用户)
     */
    private String username;
    /**
     * 随机码
     */
    private String random;
    private long expiresIn;

    public ScopeCode() {
    }

    @Override
    public String getId() {
        return code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

}
