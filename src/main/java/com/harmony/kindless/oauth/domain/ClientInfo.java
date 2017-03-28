package com.harmony.kindless.oauth.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.harmony.umbrella.data.domain.BaseEntity;

/**
 * @author wuxii@foxmail.com
 */
@Entity
@Table(name = "K_O2_CLIENT_INFO")
public class ClientInfo extends BaseEntity<String> {

    private static final long serialVersionUID = -410739402238643963L;

    public static final ClientInfo DEFAULT_APP = new ClientInfo();

    static {
        DEFAULT_APP.clientId = "915910274";
        DEFAULT_APP.clientSecret = "91b8201892c8c56d9336c690afb3e0a1";
        DEFAULT_APP.redirectUri = "http://www.baidu.com";
        DEFAULT_APP.expiresIn = Long.MAX_VALUE;
    }

    @Id
    private String clientId;
    private String clientSecret;
    private String redirectUri;

    private String name;
    private String site;
    private String description;
    /**
     * clientSecret失效时间
     */
    private Long expiresIn;

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

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
