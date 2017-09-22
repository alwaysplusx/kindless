package com.harmony.kindless.core.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.harmony.umbrella.data.domain.BaseEntity;

/**
 * @author wuxii@foxmail.com
 */
@Entity
@Table(name = "K_MODULE")
public class Module extends BaseEntity<String> {

    private static final long serialVersionUID = 8334142429768727568L;

    @Id
    private String code;
    private String name;
    private String path;
    private String icon;
    @Column(nullable = false)
    private int ordinal;
    private String remark;

    @OneToMany(mappedBy = "module")
    private List<Permission> permissions;

    @OneToMany(mappedBy = "parent", cascade = { CascadeType.REMOVE })
    private List<Module> children;

    @ManyToOne(optional = true)
    @JoinColumn(name = "parent", referencedColumnName = "code")
    private Module parent;

    public Module() {
    }

    public Module(String code) {
        this.code = code;
    }

    public Module(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Module(String code, String name, String path, String icon) {
        this.code = code;
        this.name = name;
        this.path = path;
        this.icon = icon;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Module> getChildren() {
        return children;
    }

    public void setChildren(List<Module> children) {
        this.children = children;
    }

    public Module getParent() {
        return parent;
    }

    public void setParent(Module parent) {
        this.parent = parent;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

}
