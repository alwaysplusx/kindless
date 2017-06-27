package com.harmony.kindless.oauth.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.harmony.umbrella.data.domain.BaseEntity;

/**
 * @author wuxii@foxmail.com
 */
@Entity
@Table(name = "K_ACCESS_TOKEN")
public class AccessToken extends BaseEntity<String> {

    private static final long serialVersionUID = 7647629283595344698L;

    @Id
    private String accessToken;
    private int expiresIn;
    private String username;
    private String clientId;
    private String refreshToken;
    private int refreshTokenExpiresIn;
    private String scope;
    private String grantType;
    @Temporal(TemporalType.TIMESTAMP)
    private Date refreshTime;

    @Override
    public String getId() {
        return accessToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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

}
