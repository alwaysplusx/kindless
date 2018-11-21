package com.harmony.kindless.apis.domain.user;

import com.harmony.kindless.apis.domain.BaseEntity;
import com.harmony.kindless.apis.domain.Tables;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author wuxii
 */
@Getter
@Setter
@Entity
@Table(name = Tables.USER_TABLE_PREFIX + "user_account", schema = Tables.USER_SCHEMA)
public class UserAccount extends BaseEntity {

    private static final long serialVersionUID = -4294906418539267902L;
    private String account;
    private int type;
    private int status;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_account_user_id"))
    private User user;
}
