package com.le.share.security.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yinbohe.
 * Date 2020/5/4
 * Description
 */
public class User implements Serializable {

    private Long userId;

    private String userName;

    private String password;

    private List<Role> roles;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}
