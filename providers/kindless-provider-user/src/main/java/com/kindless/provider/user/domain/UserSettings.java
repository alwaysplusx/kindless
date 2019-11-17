package com.kindless.provider.user.domain;

import com.harmony.kindless.core.domain.IdEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author wuxii
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "u_user_settings")
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
