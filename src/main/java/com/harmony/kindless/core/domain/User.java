package com.harmony.kindless.core.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.harmony.kindless.data.BaseEntity;

/**
 * @author wuxii@foxmail.com
 */
@Entity
@Table(name = "K_USER")
public class User extends BaseEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(unique = true)
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String address;
    @ManyToMany
    @JoinTable(//
            name = "K_USER_ROLE", //
            joinColumns = { @JoinColumn(name = "username", referencedColumnName = "username") }, //
            inverseJoinColumns = { @JoinColumn(name = "role_code", referencedColumnName = "code") }//
    )
    private List<Role> roles;

    public User() {
    }

    public User(Long userId) {
        this.userId = userId;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Long getId() {
        return getUserId();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "{username:" + username + ", password:" + password + "}";
    }

}
