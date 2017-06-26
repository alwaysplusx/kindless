package com.harmony.kindless.domain.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author wuxii@foxmail.com
 */
@Entity
@Table(name = "K_MENU")
public class Menu implements Serializable {

    private static final long serialVersionUID = 8488575414538698805L;

    @Id
    private String code;
    private String name;
    private String link;
    private String icon;

    @OneToMany(mappedBy = "parent", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private List<Menu> childs;

    @ManyToOne(optional = true)
    @JoinColumn(name = "parent", referencedColumnName = "code")
    private Menu parent;

    public Menu() {
    }

    public Menu(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Menu(String code, String name, String link, String icon) {
        this.code = code;
        this.name = name;
        this.link = link;
        this.icon = icon;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Menu> getChilds() {
        return childs;
    }

    public void setChilds(List<Menu> childs) {
        this.childs = childs;
    }

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

}
