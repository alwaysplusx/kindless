package com.harmony.kindless.apis.domain.security;

import com.harmony.kindless.apis.domain.BaseEntity;
import com.harmony.kindless.apis.domain.Tables;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author wuxii
 */
@Getter
@Setter
@Entity
@Table(name = Tables.SECURITY_TABLE_PREFIX + "authority", schema = Tables.SECURITY_SCHEMA)
public class Authority extends BaseEntity {

    private String code;
    private String remark;

}
