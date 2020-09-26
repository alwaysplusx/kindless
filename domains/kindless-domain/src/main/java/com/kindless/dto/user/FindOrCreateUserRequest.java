package com.kindless.dto.user;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class FindOrCreateUserRequest {

    private String username;
    private String nickname;

    private String account;
    private int accountType;

}
