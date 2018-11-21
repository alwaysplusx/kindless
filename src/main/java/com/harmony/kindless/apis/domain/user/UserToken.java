package com.harmony.kindless.apis.domain.user;

import com.harmony.kindless.apis.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.harmony.kindless.apis.domain.Tables.USER_SCHEMA;
import static com.harmony.kindless.apis.domain.Tables.USER_TABLE_PREFIX;

/**
 * @author wuxii
 */
@Getter
@Setter
@Entity
@Table(
        name = USER_TABLE_PREFIX + "user_token",
        schema = USER_SCHEMA,
        uniqueConstraints = @UniqueConstraint(name = "unique_user_token", columnNames = "token")
)
public class UserToken extends BaseEntity {

    private static final long serialVersionUID = -4567581212787988921L;

    private int type;

    private String token;

    private String ipAddress;

    private LocalDateTime grantedAt;

    private LocalDateTime expiredAt;

    @ManyToOne
    @JoinColumn(name = "user_device_id", foreignKey = @ForeignKey(name = "fk_user_token_user_device_id"))
    private UserDevice userDevice;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_token_user_id"))
    private User user;

}
