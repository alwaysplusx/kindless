package com.kindless.domain.user;

import com.kindless.core.domain.BaseEntity;
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
@Table(name = "o_authority")
public class Authority extends BaseEntity {

    private static final long serialVersionUID = -7649447418458043926L;
    private String code;
    private String remark;

}
