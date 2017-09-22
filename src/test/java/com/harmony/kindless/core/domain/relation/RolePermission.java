package com.harmony.kindless.core.domain.relation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.harmony.kindless.core.domain.relation.RolePermission.RolePermissionPK;
import com.harmony.umbrella.data.domain.BaseEntity;

/**
 * 
 * @author wuxii@foxmail.com
 */
@Entity
@IdClass(RolePermissionPK.class)
@Table(name = "K_ROLE_PERMISSION")
public class RolePermission extends BaseEntity<RolePermissionPK> {

    private static final long serialVersionUID = 6575574439631722661L;

    @Id
    @Column(name = "role_code")
    private String roleCode;
    @Id
    @Column(name = "permission_code")
    private String permissionCode;

    @Override
    public RolePermissionPK getId() {
        return new RolePermissionPK(roleCode, permissionCode);
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public static class RolePermissionPK implements Serializable {

        private static final long serialVersionUID = 945442820716633080L;

        private String roleCode;
        private String permissionCode;

        public RolePermissionPK(String role, String permission) {
            this.roleCode = role;
            this.permissionCode = permission;
        }

        public String getRoleCode() {
            return roleCode;
        }

        public void setRoleCode(String roleCode) {
            this.roleCode = roleCode;
        }

        public String getPermissionCode() {
            return permissionCode;
        }

        public void setPermissionCode(String permissionCode) {
            this.permissionCode = permissionCode;
        }

    }

}
