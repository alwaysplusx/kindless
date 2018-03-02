package com.harmony.kindless.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.harmony.umbrella.data.domain.BaseEntity;

/**
 * @author wuxii@foxmail.com
 */
@Entity
@Table(name = "K_TOKEN")
public class Token extends BaseEntity<String> {

    private static final long serialVersionUID = -6426816488499627125L;

    /**
     * token主体
     */
    @Id
    private String token;
    /**
     * token相关的用户
     */
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", updatable = false)
    private User user;
    /**
     * 与服务器绑定的session
     */
    private String sessionId;
    /**
     * 生成token的随机数
     */
    @Column(updatable = false)
    private String random;

    @Column(updatable = false)
    private String device;

    private String host;

    @Override
    public String getId() {
        return getToken();
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

}
