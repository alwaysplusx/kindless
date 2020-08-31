package com.kindless.user.domain;

import com.kindless.core.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wuxii
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(
        name = "u_user_token",
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
