package com.harmony.kindless.apis.domain.user;

import com.harmony.kindless.apis.domain.IdEntity;
import com.harmony.kindless.apis.domain.Tables;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author wuxii
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = Tables.USER_TABLE_PREFIX + "user_details", schema = Tables.USER_SCHEMA)
public class UserDetails extends IdEntity {

    private static final long serialVersionUID = 196410431484362356L;

    private LocalDate birthday;

    private Long areaId;

    @OneToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_details_user_id"))
    private User user;

    public UserDetails(User user) {
        this.user = user;
    }

}
