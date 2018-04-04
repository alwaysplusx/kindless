package com.harmony.kindless.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.harmony.kindless.data.BaseEntity;

/**
 * 授权凭证
 * 
 * @author wuxii@foxmail.com
 */
@Entity
@Table(name = "K_CERTIFICATE")
public class Certificate extends BaseEntity<Long> {

    private static final long serialVersionUID = -6426816488499627125L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificateId;
    /**
     * token
     */
    @Column(length = 500, unique = true, updatable = false, nullable = false)
    private String token;

    /**
     * 此凭证的相关用户
     */
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    /**
     * 此凭证相关的客户端
     */
    @ManyToOne
    @JoinColumn(name = "clientId", referencedColumnName = "clientId")
    private ClientInfo clientInfo;

    /**
     * 生成token的随机数
     */
    @Column(updatable = false)
    private String random;

    @Column(updatable = false)
    private String device;

    @Column(updatable = false)
    private String host;

    /**
     * 颁发时间(起效时间)
     */
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date issuedAt;

    /**
     * 有效时长(秒)
     */
    private int expiresIn;

    /**
     * 与服务器绑定的session
     */
    private String sessionId;

    @Override
    public Long getId() {
        return getCertificateId();
    }

    public Long getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(Long certificateId) {
        this.certificateId = certificateId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public boolean isExpired() {
        return issuedAt == null ? true : issuedAt.getTime() + expiresIn * 1000 <= System.currentTimeMillis();
    }

}
