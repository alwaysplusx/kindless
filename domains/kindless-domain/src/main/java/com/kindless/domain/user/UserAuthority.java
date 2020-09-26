package com.kindless.domain.user;

import com.kindless.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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

    private Long authorityId;

    private Long userId;

}
