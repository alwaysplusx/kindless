package com.harmony.kindless.apis.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity extends IdEntity {

    private static final long serialVersionUID = -3237020760651522386L;

    @Column(updatable = false)
    protected String createdBy;

    @Column(updatable = false)
    protected Date createdAt;

    protected String updatedBy;

    protected Date updatedAt;

}