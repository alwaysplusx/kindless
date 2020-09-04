package com.kindless.domain.user;

import com.kindless.core.domain.IdEntity;
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
@NoArgsConstructor
@Entity
@Table(name = "u_user_details")
public class UserDetails extends IdEntity {

    private static final long serialVersionUID = 196410431484362356L;

    private Long areaId;
    private String avatar;
    private Date birthday;

    @OneToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_details_user_id"))
    private User user;

    public UserDetails(User user) {
        this.user = user;
    }

}
