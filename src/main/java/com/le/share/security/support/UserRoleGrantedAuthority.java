package com.le.share.security.support;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by yinbohe.
 * Date 2020/5/4
 * Description
 */
public class UserRoleGrantedAuthority implements GrantedAuthority {
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;

    public UserRoleGrantedAuthority(Long roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

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

    @Override
    public String getAuthority() {
        return roleName == null ? String.valueOf(roleId) : roleName;
    }

}
