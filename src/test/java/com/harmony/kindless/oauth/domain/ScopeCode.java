package com.harmony.kindless.oauth.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.harmony.kindless.oauth.domain.ScopeCode.ScopeCodeId;
import com.harmony.umbrella.data.domain.BaseEntity;

/**
 * @author wuxii@foxmail.com
 */
@Entity
@IdClass(ScopeCodeId.class)
@Table(name = "K_SCOPE_CODE")
public class ScopeCode extends BaseEntity<ScopeCodeId> {

    private static final long serialVersionUID = 7545634150299041555L;

    @Id
    private String code;
    @Id
    private String clientId;
    /**
     * 授权的用户(当前登录的用户)
     */
    @Id
    private Long userId;
    /**
     * 用户选择的权限范围
     */
    private String scopes;
    private long expiresIn;

    public ScopeCode() {
    }

    @Override
    public ScopeCodeId getId() {
        return new ScopeCodeId(code, userId, clientId);
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public boolean isExpired() {
        return createdTime == null ? true : (createdTime.getTime() + expiresIn * 1000) < System.currentTimeMillis();
    }

    public static class ScopeCodeId implements Serializable {

        private static final long serialVersionUID = 6630563811698795774L;

        private String code;
        private Long userId;
        private String clientId;

        public ScopeCodeId() {
        }

        public ScopeCodeId(String code, Long userId, String clientId) {
            this.code = code;
            this.userId = userId;
            this.clientId = clientId;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

    }

}
