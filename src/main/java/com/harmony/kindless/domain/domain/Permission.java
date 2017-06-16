package com.harmony.kindless.domain.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.harmony.umbrella.data.domain.BaseEntity;

@Entity
@Table(name = "K_PERMISSION")
public class Permission extends BaseEntity<String> {

    private static final long serialVersionUID = 6179088839462016654L;
    @Id
    private String code;
    private String name;
    private String description;
    @ManyToMany(mappedBy = "permissions")
    private List<Role> roles;

    public Permission() {
    }

    public Permission(String code, String name) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "{code:" + code + ", name:" + name + "}";
    }

}
