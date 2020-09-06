package com.kindless.user.dto;

import com.kindless.core.auditor.Auditor;

/**
 * @author wuxin
 */
public class AuditorTokenDto {

    private Auditor auditor;
    private String token;

    public AuditorTokenDto(String token, Auditor auditor) {
        this.token = token;
        this.auditor = auditor;
    }

    public String getUsername() {
        return auditor.getUsername();
    }

    public Long getUserId() {
        return auditor.getUserId();
    }

    public String getToken() {
        return token;
    }

}
