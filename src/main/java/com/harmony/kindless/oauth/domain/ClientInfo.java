package com.harmony.kindless.oauth.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.harmony.kindless.core.domain.User;
import com.harmony.umbrella.data.domain.BaseEntity;

/**
 * @author wuxii@foxmail.com
 */
@Entity
@Table(name = "K_CLIENT_INFO")
public class ClientInfo extends BaseEntity<String> {

    private static final long serialVersionUID = -410739402238643963L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String clientId;
    private String clientSecret;
    private String redirectUri;

    private String name;
    private String description;
    /**
     * clientSecret失效时长
     */
    private long expiresIn;
    /**
     * clientSecret刷新的时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date refreshTime;

    @ManyToOne
    @JoinColumn(name = "ownerId", referencedColumnName = "userId")
    private User owner;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "virtualUserId", referencedColumnName = "userId")
    private User virtualUser;

    public ClientInfo() {
    }

    public ClientInfo(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String getId() {
        return clientId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(Date refreshTime) {
        this.refreshTime = refreshTime;
    }

    public boolean isExpired() {
        return expiresIn == -1 || refreshTime == null ? false : (refreshTime.getTime() + expiresIn * 1000) < System.currentTimeMillis();
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getVirtualUser() {
        return virtualUser;
    }

    public void setVirtualUser(User virtualUser) {
        this.virtualUser = virtualUser;
    }

}
