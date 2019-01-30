package com.harmony.kindless.apis.dto;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wuxii
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSecurityData implements Serializable {

    private Long userId;
    private String username;
    private String password;
    private List<String> authorities = new ArrayList<>();
    private boolean accountExpired;
    private boolean passwordExpired;
    private boolean accountEnabled;

}
