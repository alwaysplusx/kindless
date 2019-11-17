package com.harmony.kindless.core.dto;

import com.harmony.kindless.core.domain.BaseEntity;

/**
 * @author wuxii
 */
public interface IdDto {

    Long getId();

    static IdDto of(BaseEntity entity) {
        return entity::getId;
    }

}
