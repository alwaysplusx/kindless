package com.harmony.kindless.apis.domain.user;

import com.harmony.kindless.apis.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

import static com.harmony.kindless.apis.domain.Tables.USER_SCHEMA;
import static com.harmony.kindless.apis.domain.Tables.USER_TABLE_PREFIX;

/**
 * @author wuxii
 */
@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
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

    @Temporal(TemporalType.TIMESTAMP)
    private Date grantedAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredAt;

    private String userAgent;

    private String serialNumber;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_token_user_id"))
    private User user;

}
