package com.le.share.security.domain;

/**
 * Created by yinbohe.
 * Date 2020/5/4
 * Description
 */
public class Role {
    /**
     *
     */
    private Long roleId;
    /**
     *
     */
    private String roleName;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
