package com.harmony.kindless.domain.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.harmony.umbrella.data.domain.BaseEntity;

/**
 * @author wuxii@foxmail.com
 */
@Entity
@Table(name = "K_ROLE")
public class Role extends BaseEntity<String> {

    private static final long serialVersionUID = 3438518466546077376L;
    @Id
    private String code;
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(//
            name = "K_ROLE_PERMISSION", //
            joinColumns = { @JoinColumn(name = "role_code", referencedColumnName = "code") }, //
            inverseJoinColumns = { @JoinColumn(name = "permission_code", referencedColumnName = "code") }//
    )
    private List<Permission> permissions;

    public Role() {
    }

    public Role(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getId() {
        return code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "{code:" + code + ", name:" + name + "}";
    }

}
