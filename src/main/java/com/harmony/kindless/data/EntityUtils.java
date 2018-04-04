package com.harmony.kindless.data;

import java.util.Date;

import com.harmony.kindless.shiro.PrimaryPrincipal;
import com.harmony.kindless.util.SecurityUtils;

/**
 * @author wuxii@foxmail.com
 */
public class EntityUtils {

    public static void applyUserInfoIfNecessary(BaseEntity<?> entity) {
        applyCreatorInfoIfNecessary(entity);
        applyModifierInfoIfNecessary(entity);
    }

    public static void applyCreatorInfoIfNecessary(BaseEntity<?> entity) {
        if (entity.getCreatedTime() == null) {
            entity.setCreatedTime(new Date());
        }
        PrimaryPrincipal pp = getPrimaryPrincipal();
        if (pp != null) {
            if (entity.getCreatorId() == null) {
                entity.setCreatorId(pp.getUserId());
            }
            if (entity.getCreatorCode() == null) {
                entity.setCreatorCode(pp.getUsername());
            }
            if (entity.getCreatorName() == null) {
                entity.setCreatorName(pp.getNickname());
            }
            if (entity.getCreatorClientId() == null) {
                entity.setCreatorClientId(pp.getClientId());
            }
        }
    }

    public static void applyModifierInfoIfNecessary(BaseEntity<?> entity) {
        if (entity.getModifiedTime() == null) {
            entity.setModifiedTime(new Date());
        }
        PrimaryPrincipal pp = getPrimaryPrincipal();
        if (pp != null) {
            if (entity.getModifierId() == null) {
                entity.setModifierId(pp.getUserId());
            }
            if (entity.getModifierCode() == null) {
                entity.setModifierCode(pp.getUsername());
            }
            if (entity.getModifierName() == null) {
                entity.setModifierName(pp.getNickname());
            }
            if (entity.getModifierClientId() == null) {
                entity.setModifierClientId(pp.getClientId());
            }
        }
    }

    private static PrimaryPrincipal getPrimaryPrincipal() {
        try {
            return SecurityUtils.getPrimaryPrincipal();
        } catch (Throwable e) {
            return null;
        }
    }

}
