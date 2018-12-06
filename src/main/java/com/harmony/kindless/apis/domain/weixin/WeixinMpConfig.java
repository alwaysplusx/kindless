package com.harmony.kindless.apis.domain.weixin;

import com.harmony.kindless.apis.domain.BaseEntity;
import com.harmony.kindless.apis.domain.Tables;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author wuxii
 */
@Getter
@Setter
@Entity
@Table(name = Tables.CONFIG_TABLE_PREFIX + "wx_mp_config", schema = Tables.CONFIG_SCHEMA)
public class WeixinMpConfig extends BaseEntity {

    private String appId;
    private String secret;
    private String token;
    private String redirectUri;
    private String aesKey;
    private String templateId;

    private int proxyPort;
    private String proxyHost;
    private String proxyUsername;
    private String proxyPassword;

    private boolean allowAutoRefreshToken;

    private String accessToken;
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiresTime;

    private String jsapiTicket;
    @Temporal(TemporalType.TIMESTAMP)
    private Date jsapiTicketExpiresTime;

    private String cardApiTicket;
    @Temporal(TemporalType.TIMESTAMP)
    private Date cardApiTicketExpiresTime;

}
