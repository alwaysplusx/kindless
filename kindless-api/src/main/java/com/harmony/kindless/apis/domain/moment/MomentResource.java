package com.harmony.kindless.apis.domain.moment;

import com.harmony.kindless.apis.domain.IdEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author wuxii
 */
@Getter
@Setter
@Entity
@Table(name = "b_moment_resource")
public class MomentResource extends IdEntity {

    private String path;
    private int type;

    @ManyToOne
    @JoinColumn(name = "moment_id")
    private Moment moment;

}
