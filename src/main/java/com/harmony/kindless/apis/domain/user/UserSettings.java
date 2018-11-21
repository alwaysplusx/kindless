package com.harmony.kindless.apis.domain.user;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.harmony.kindless.apis.domain.IdEntity;
import com.harmony.kindless.apis.domain.Tables;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author wuxii
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = Tables.USER_TABLE_PREFIX + "user_settings", schema = Tables.USER_SCHEMA)
public class UserSettings extends IdEntity {

    private static final long serialVersionUID = -4127256128374148216L;

    private boolean notificationEnabled;

    @OneToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_settings_user_id"))
    private User user;

    public UserSettings(User user) {
        this.user = user;
    }

}
