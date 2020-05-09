package com.le.share.security.service;

import com.le.share.mapper.ext.ExtUserInfoMapper;
import com.le.share.model.UserInfo;
import com.le.share.security.domain.User;
import com.le.share.security.domain.UserGrantedAuthority;
import com.le.share.security.exception.AuthenticationSecurityException;
import com.le.share.security.support.UserDetailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by yinbohe.
 * Date 2020/5/4
 * Description
 */
@Component("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private ExtUserInfoMapper extUserInfoMapper;

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        Optional<UserInfo> optional = extUserInfoMapper.selectByOpenId(token);

        UserInfo userInfo = optional.orElseThrow(
                () -> new AuthenticationSecurityException("用户请求认证失败")
        );

        UserDetailInfo detail = new UserDetailInfo();
        User user = new User();
        user.setUserId(userInfo.getId());
        user.setUserName(userInfo.getNickName());
        detail.setUser(user);
        List<GrantedAuthority> authority = new ArrayList<>();
        authority.add(new UserGrantedAuthority());
        detail.setAuthorities(authority);
        detail.setAuthType("Mac");
        return detail;
    }
}
