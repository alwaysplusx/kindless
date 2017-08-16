package com.harmony.kindless.util;

import static com.harmony.kindless.util.SecurityUtils.*;

import java.util.Date;

import com.harmony.umbrella.data.domain.BaseEntity;

/**
 * @author wuxii@foxmail.com
 */
public final class WebUtils {

    public static void applyUserInfoIfNecessary(BaseEntity<?> entity) {
        applyCreatorInfoIfNecessary(entity);
        applyModifierInfoIfNecessary(entity);
    }

    public static void applyCreatorInfoIfNecessary(BaseEntity<?> entity) {
        entity.setCreatedTime(new Date());
        entity.setCreatorCode(getUsername());
        entity.setCreatorId(getUserId());
        entity.setCreatorName(getNickname());
    }

    public static void applyModifierInfoIfNecessary(BaseEntity<?> entity) {
        entity.setModifiedTime(new Date());
        entity.setModifierCode(getUsername());
        entity.setModifierId(getUserId());
        entity.setModifierName(getNickname());
    }

}
