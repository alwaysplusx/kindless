package com.harmony.kindless.apis.domain.user;

import static com.harmony.kindless.apis.domain.Tables.USER_SCHEMA;
import static com.harmony.kindless.apis.domain.Tables.USER_TABLE_PREFIX;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.harmony.kindless.apis.domain.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wuxii
 */
@Getter
@Setter
@Entity
@Table(name = USER_TABLE_PREFIX + "user_device", schema = USER_SCHEMA)
public class UserDevice extends BaseEntity {

    private static final long serialVersionUID = 581631374204245581L;

    private String name;

    private int type;

    private String serialNumber;

    private LocalDateTime firstUsedAt;

    private LocalDateTime lastUsedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_device_user_id"))
    private User user;

}
