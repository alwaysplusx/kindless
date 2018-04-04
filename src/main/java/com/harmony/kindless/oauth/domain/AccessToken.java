package com.harmony.kindless.oauth.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.harmony.kindless.core.domain.Certificate;
import com.harmony.kindless.core.domain.ClientInfo;
import com.harmony.kindless.core.domain.User;
import com.harmony.kindless.data.BaseEntity;

/**
 * 通过oauth 2.0得到的access token
 * 
 * @author wuxii@foxmail.com
 */
@Entity
@Table(name = "K_ACCESS_TOKEN")
public class AccessToken extends BaseEntity<Long> {

    private static final long serialVersionUID = 7647629283595344698L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accessTokenId;

    private String grantType;

    /**
     * 授权凭证(包含有access token)
     */
    @OneToOne
    @JoinColumn(name = "certificateId", referencedColumnName = "certificateId")
    private Certificate certificate;

    /**
     * 授予的权限范围
     */
    @OneToOne
    @JoinColumn(name = "scopeCodeId", referencedColumnName = "scopeCodeId")
    private ScopeCode scopeCode;

    public AccessToken() {
    }

    @Override
    public Long getId() {
        return getAccessTokenId();
    }

    public Long getAccessTokenId() {
        return accessTokenId;
    }

    public void setAccessTokenId(Long accessTokenId) {
        this.accessTokenId = accessTokenId;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public ScopeCode getScopeCode() {
        return scopeCode;
    }

    public void setScopeCode(ScopeCode scopeCode) {
        this.scopeCode = scopeCode;
    }

    public String getAccessToken() {
        return certificate != null ? certificate.getToken() : null;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public User getUser() {
        return certificate == null ? null : certificate.getUser();
    }

    public Long getUserId() {
        return getUserId() == null ? null : getUser().getUserId();
    }

    public String getUsername() {
        return getUserId() == null ? null : getUser().getUsername();
    }

    public ClientInfo getClientInfo() {
        return certificate == null ? null : certificate.getClientInfo();
    }

    public String getClientId() {
        return getClientInfo() == null ? null : getClientInfo().getClientId();
    }

    public String getClientSecret() {
        return getClientInfo() == null ? null : getClientInfo().getClientSecret();
    }

    public int getExpiresIn() {
        return certificate == null ? 0 : certificate.getExpiresIn();
    }

}
