package com.kindless.apis.dto;

import com.kindless.apis.domain.BaseEntity;

/**
 * @author wuxii
 */
public interface IdDto {

    Long getId();

    static IdDto of(BaseEntity entity) {
        return entity::getId;
    }

}
