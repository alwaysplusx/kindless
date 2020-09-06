package com.kindless.user.dto;

import com.kindless.domain.user.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author wuxii
 */
@Data
public class UserDto {

    private Long userId;
    private String username;
    private String avatar;

    public UserDto() {
    }

    public UserDto(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.avatar = user.getAvatar();
    }

}
