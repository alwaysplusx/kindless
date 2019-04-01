package com.harmony.kindless.apis.domain.moment;

import com.harmony.kindless.apis.domain.BaseEntity;
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
    public static final int RESOURCE_OF_PICTURE = 1;
    public static final int RESOURCE_OF_VIDEO = 2;

    private int type;
    private String path;

    @ManyToOne
    @JoinColumn(name = "moment_id", foreignKey = @ForeignKey(name = "fk_moment_resource_moment_id"))
    private Moment moment;

    @Column(name = "moment_id", updatable = false, insertable = false)
    private Long momentId;

}
