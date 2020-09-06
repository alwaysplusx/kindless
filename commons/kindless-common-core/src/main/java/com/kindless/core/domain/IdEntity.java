package com.kindless.core.domain;

import org.springframework.data.domain.Persistable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author wuxii
 */
@MappedSuperclass
public abstract class IdEntity implements Persistable<Long> {

    @Id
    protected Long id;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean isNew() {
        return getId() == null;
    }

}
