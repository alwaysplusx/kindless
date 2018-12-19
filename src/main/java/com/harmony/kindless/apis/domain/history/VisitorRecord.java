package com.harmony.kindless.apis.domain.history;

import com.harmony.kindless.apis.domain.IdEntity;
import com.harmony.kindless.apis.domain.Tables;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author wuxii
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = Tables.HISTORY_TABLE_PREFIX + "visitor_record", schema = Tables.HISTORY_SCHEMA)
public class VisitorRecord extends IdEntity {

    private Long userId;
    private Long visitorId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date visitAt;

    private String remark;
    private Long resourceId;
    private int type;

}
