package com.harmony.kindless.apis.domain.core;

import com.harmony.kindless.apis.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author wuxii
 */
@Getter
@Setter
@Entity
@Table(
        name = "u_user_authority",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_user_authority",
                        columnNames = {"user_id", "authority_id"}
                )
        }
)
public class UserAuthority extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 7286252717124272544L;

    @OneToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_authority_user_id"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "authority_id", foreignKey = @ForeignKey(name = "fk_user_authority_authority_id"))
    private Authority authority;

    @Column(name = "authority_id", updatable = false, insertable = false)
    private Long authorityId;

    @Column(name = "user_id", updatable = false, insertable = false)
    private Long userId;

}
