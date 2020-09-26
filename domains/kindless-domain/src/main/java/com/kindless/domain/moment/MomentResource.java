package com.kindless.domain.moment;

import com.kindless.core.domain.BaseEntity;
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
@Table(name = "m_moment_resource")
public class MomentResource extends BaseEntity {

    private static final long serialVersionUID = 1450339484985699150L;

    private String type;
    private String path;

    @ManyToOne
    @JoinColumn(name = "moment_id", foreignKey = @ForeignKey(name = "fk_moment_resource_moment_id"))
    private Moment moment;

    @Column(name = "moment_id", updatable = false, insertable = false)
    private Long momentId;

}
