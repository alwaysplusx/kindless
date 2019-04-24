package com.harmony.kindless.apis.dto;

import com.harmony.kindless.apis.domain.BaseEntity;

/**
 * @author wuxii
 */
public interface IdDto {

    Long getId();

    static IdDto of(BaseEntity entity) {
        return entity::getId;
    }

}
