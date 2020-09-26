package com.kindless.domain.user;

import com.kindless.core.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author wuxii
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "u_user_settings")
public class UserSettings extends BaseEntity {

    private static final long serialVersionUID = -4127256128374148216L;

    private boolean notificationEnabled;

    private long userId;

}
