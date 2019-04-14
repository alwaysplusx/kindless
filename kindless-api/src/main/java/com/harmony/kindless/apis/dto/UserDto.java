package com.harmony.kindless.apis.dto;

import com.harmony.kindless.apis.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author wuxii
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private Long userId;
    private String username;
    private String avatar;

    public UserDto(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.avatar = user.getAvatar();
    }

}
