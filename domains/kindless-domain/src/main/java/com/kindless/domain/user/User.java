package com.kindless.domain.user;

import com.kindless.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "u_user")
public class User extends BaseEntity {

    public static final String ACCOUNT_LOCKED = "locked";
    public static final String ACCOUNT_UNLOCKED = "unlocked";

    private String username;
    private String nickname;
    private String password;
    private String avatar;
    private String gender;
    private String remark;
    private Date registerAt;
    private Date passwordExpiredAt;
    private String accountStatus;
    private Date accountExpiredAt;

    public User() {
    }

    public User(Long userId) {
        this.setId(userId);
    }

    public boolean isLocked() {
        return accountStatus == ACCOUNT_LOCKED;
    }

    public boolean isEnabled() {
        return !isLocked();
    }

}
