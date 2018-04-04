package com.harmony.kindless.oauth.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.harmony.kindless.core.domain.ClientInfo;
import com.harmony.kindless.core.domain.User;
import com.harmony.kindless.data.BaseEntity;

/**
 * @author wuxii@foxmail.com
 */
@Entity
@Table(name = "K_SCOPE_CODE")
public class ScopeCode extends BaseEntity<Long> {

    private static final long serialVersionUID = 7545634150299041555L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scopeCodeId;

    @Column(unique = true, updatable = false)
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
     * scope token的起效时间
     */
    @Column(updatable = false)
    private Date issuedAt = new Date();
    /**
     * 用户选择的权限范围
     */
    private String scopes;
    /**
     * 有效时长(秒)
     */
    private int expiresIn;

    public ScopeCode() {
    }

    @Override
    public Long getId() {
        return getScopeCodeId();
    }

    public Long getScopeCodeId() {
        return scopeCodeId;
    }

    public void setScopeCodeId(Long scopeCodeId) {
        this.scopeCodeId = scopeCodeId;
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

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public boolean isExpired() {
        return issuedAt == null ? true : (issuedAt.getTime() + expiresIn * 1000) < System.currentTimeMillis();
    }

}
