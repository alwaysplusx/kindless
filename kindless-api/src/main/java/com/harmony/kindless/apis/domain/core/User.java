package com.harmony.kindless.apis.domain.core;

import com.harmony.kindless.apis.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "u_user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 543961445644373533L;

    private String username;
    private String nickname;
    private String password;
    private int gender;
    private String remark;
    private Date registerAt;
    private Date passwordExpiredAt;
    private int accountStatus;
    private Date accountExpiredAt;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private UserSettings userSettings;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private UserDetails userDetails;

    public User() {
    }

    public User(Long userId) {
        this.id = userId;
    }

    public boolean isEnabled() {
        return accountStatus == 1;
    }

}
