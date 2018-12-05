package com.harmony.kindless.apis.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wuxii
 */
@Getter
@Setter
@Builder
public class UserTokenClaims {
    
    private String id;
    private String userAgent;
    private String ipAddress;
    private String serialNumber;

}
