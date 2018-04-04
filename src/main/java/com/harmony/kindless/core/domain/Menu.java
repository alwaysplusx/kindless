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

import com.harmony.kindless.data.BaseEntity;

/**
 * @author wuxii@foxmail.com
 */
@Entity
@Table(name = "K_MENU")
public class Menu extends BaseEntity<String> {

    private static final long serialVersionUID = 8334142429768727568L;

    @Id
    private String code;
    private String name;
    private String path;
    private String icon;
    @Column(nullable = false)
    private int ordinal;
    private String remark;

    @OneToMany(mappedBy = "menu")
    private List<Permission> permissions;

    @OneToMany(mappedBy = "parent", cascade = { CascadeType.REMOVE })
    private List<Menu> children;

    @ManyToOne(optional = true)
    @JoinColumn(name = "parent", referencedColumnName = "code")
    private Menu parent;

    public Menu() {
    }

    public Menu(String code) {
        this.code = code;
    }

    public Menu(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Menu(String code, String name, String path, String icon) {
        this.code = code;
        this.name = name;
        this.path = path;
        this.icon = icon;
    }

    @Override
    public String getId() {
        return getCode();
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

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

}
