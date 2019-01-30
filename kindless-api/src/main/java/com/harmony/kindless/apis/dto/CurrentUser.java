package com.harmony.kindless.apis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author wuxii
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CurrentUser {

    private Long userId;
    private String username;
    private String nickname;
    private String userAgent;
    private String device;

}
