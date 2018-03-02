package com.harmony.kindless.core.domain.relation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.harmony.kindless.core.domain.relation.PermissionResource.PermissionResourcePK;
import com.harmony.umbrella.data.domain.BaseEntity;

/**
 * 
 * @author wuxii@foxmail.com
 */
@Entity
@IdClass(PermissionResourcePK.class)
@Table(name = "K_PERMISSION_RESOURCE")
public class PermissionResource extends BaseEntity<PermissionResourcePK> {

    private static final long serialVersionUID = -1406585130449092160L;

    @Id
    @Column(name = "permission_code")
    private String permissionCode;
    @Id
    @Column(name = "resource_id")
    private Long resourceId;

    @Override
    public PermissionResourcePK getId() {
        return new PermissionResourcePK(permissionCode, resourceId);
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public static class PermissionResourcePK implements Serializable {

        private static final long serialVersionUID = -5623129948989658942L;
        private String permissionCode;
        private Long resourceId;

        public PermissionResourcePK() {
        }

        public PermissionResourcePK(String permission, Long resource) {
            this.permissionCode = permission;
            this.resourceId = resource;
        }

        public String getPermissionCode() {
            return permissionCode;
        }

        public void setPermissionCode(String permissionCode) {
            this.permissionCode = permissionCode;
        }

        public Long getResourceId() {
            return resourceId;
        }

        public void setResourceId(Long resourceId) {
            this.resourceId = resourceId;
        }

    }

}
