package com.kindless.core.domain;

import com.harmony.umbrella.data.config.support.DataAuditingEntityListener;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners({DataAuditingEntityListener.class, AuditingEntityListener.class})
public class BaseEntity extends IdEntity {

    @CreatedBy
    @Column(updatable = false)
    protected Long createdBy;

    @CreatedDate
    @Column(updatable = false)
    protected Date createdAt;

    @LastModifiedBy
    protected Long updatedBy;

    @LastModifiedDate
    protected Date updatedAt;

}
