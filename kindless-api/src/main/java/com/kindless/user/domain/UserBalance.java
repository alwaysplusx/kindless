package com.kindless.user.domain;

import com.kindless.apis.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author wuxin
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "u_user_balance")
public class UserBalance extends BaseEntity {

    private BigDecimal availableAmount;
    private BigDecimal frozenAmount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "user_id", updatable = false, insertable = false)
    private Long userId;

}
