package com.harmony.kindless.core.domain.relation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.harmony.kindless.core.domain.relation.UserRole.UserRolePK;
import com.harmony.umbrella.data.domain.BaseEntity;

/**
 * 
 * @author wuxii@foxmail.com
 */
@Entity
@IdClass(UserRolePK.class)
@Table(name = "K_USER_ROLE")
public class UserRole extends BaseEntity<UserRolePK> {

    private static final long serialVersionUID = -7523502948622104894L;

    @Id
    private String username;
    @Id
    @Column(name = "role_code")
    private String roleCode;

    @Override
    public UserRolePK getId() {
        return new UserRolePK(username, roleCode);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public static class UserRolePK implements Serializable {

        private static final long serialVersionUID = -5527427828029005350L;
        private String username;
        private String roleCode;

        public UserRolePK(String user, String role) {
            this.username = user;
            this.roleCode = role;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getRoleCode() {
            return roleCode;
        }

        public void setRoleCode(String roleCode) {
            this.roleCode = roleCode;
        }

    }

}
