package com.le.share.security.domain;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by yinbohe.
 * Date 2020/5/3
 * Description
 */
public class UserGrantedAuthority implements GrantedAuthority {

    @Override
    public String getAuthority() {
        return "";
    }
}
