package com.harmony.kindless.oauth.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.harmony.kindless.core.domain.User;
import com.harmony.umbrella.data.domain.BaseEntity;

/**
 * @author wuxii@foxmail.com
 */
@Entity
@Table(name = "K_ACCESS_TOKEN")
public class AccessToken extends BaseEntity<String> {

    private static final long serialVersionUID = 7647629283595344698L;

    @Id
    @Column(length = 500)
    private String accessToken;
    private int expiresIn;
    @Column(unique = true)
    private String refreshToken;
    private int refreshTokenExpiresIn;
    private String scope;
    private String grantType;
    @Temporal(TemporalType.TIMESTAMP)
    private Date refreshTime;

    // resource owner
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    // third-part
    @ManyToOne
    @JoinColumn(name = "clientId", referencedColumnName = "clientId")
    private ClientInfo clientInfo;

    @Override
    public String getId() {
        return accessToken;
    }

    public String getUsername() {
        return user == null ? null : user.getUsername();
    }

    @Transient
    public Long getUserId() {
        return user == null ? null : user.getUserId();
    }

    @Transient
    public String getClientId() {
        return clientInfo == null ? null : clientInfo.getClientId();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public Date getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(Date refreshTime) {
        this.refreshTime = refreshTime;
    }

    public int getRefreshTokenExpiresIn() {
        return refreshTokenExpiresIn;
    }

    public void setRefreshTokenExpiresIn(int refreshTokenExpiresIn) {
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    }

    public boolean isRefreshTokenExpired() {
        return isExpired(refreshTokenExpiresIn);
    }

    public boolean isExpired() {
        return isExpired(expiresIn);
    }

    private boolean isExpired(int sec) {
        return refreshTime == null ? true : (sec * 1000 + refreshTime.getTime()) < System.currentTimeMillis();
    }

    public void setUser(Long userId) {
        this.setUser(new User(userId));
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setClientInfo(String clientId) {
        this.setClientInfo(new ClientInfo(clientId));
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

}
