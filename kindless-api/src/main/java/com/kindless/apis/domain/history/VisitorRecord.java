package com.kindless.apis.domain.history;

import com.kindless.apis.domain.IdEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author wuxii
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "s_visitor_record")
public class VisitorRecord extends IdEntity {

    /**
     *
     */
    private static final long serialVersionUID = -3497495789625022814L;
    private Long userId;
    private Long visitorId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date visitAt;

    private String remark;
    private Long resourceId;
    private int type;

}
