package com.harmony.kindless.apis.domain.user;

import com.harmony.kindless.apis.domain.BaseEntity;
import com.harmony.kindless.apis.domain.Tables;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = Tables.USER_TABLE_PREFIX + "user", schema = Tables.USER_SCHEMA)
public class User extends BaseEntity {

    private static final long serialVersionUID = 543961445644373533L;

    private String username;
    private String nickname;
    private String password;

    private LocalDateTime registrationTime;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private UserSettings userSettings;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private UserDetails userDetails;

}
