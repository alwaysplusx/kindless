package com.kindless.domain.user;

import com.kindless.core.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author wuxii
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "u_user_details")
public class UserDetails extends BaseEntity {

    private static final long serialVersionUID = 196410431484362356L;

    private Long areaId;
    private String avatar;
    private Date birthday;
    private long userId;

}
