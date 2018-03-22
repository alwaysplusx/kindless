package com.harmony.kindless.oauth.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.harmony.kindless.core.domain.User;
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

    /**
     * scope code相关联的客户端, 用户授予的第三方
     */
    @ManyToOne
    @JoinColumn(name = "clientId", referencedColumnName = "clientId")
    private ClientInfo clientInfo;

    /**
     * scope相关联的用户, 授权用户
     */
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    /**
     * 用户选择的权限范围
     */
    private String scopes;
    private long expiresIn;

    public ScopeCode() {
    }

    @Override
    public String getId() {
        return getCode();
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
        return clientInfo != null ? clientInfo.getClientId() : null;
    }

    public Long getUserId() {
        return user != null ? user.getUserId() : null;
    }

    public String getUsername() {
        return user != null ? user.getUsername() : null;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

}
