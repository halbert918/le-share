package com.le.share.security.mac;

import com.le.share.security.domain.User;
import com.le.share.security.domain.UserGrantedAuthority;
import com.le.share.security.support.AuthenticationToken;
import com.le.share.security.support.UserDetailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinbohe.
 * Date 2020/5/4
 * Description
 */
@Component
public class MacTokenAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    MacTokenAuthenticationToken authenticationToken = (MacTokenAuthenticationToken) authentication;

    if (((MacTokenAuthenticationToken) authentication).getId().equals("anonymous")) {
      return new AuthenticationToken(authenticationToken.getId(), getAnonymous());
    }

    // TODO 先通过缓存查询
    UserDetailInfo userDetail = (UserDetailInfo) userDetailsService.loadUserByUsername(
            ((MacTokenAuthenticationToken) authentication).getId()
    );
    return new AuthenticationToken(authenticationToken.getId(), userDetail);
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return clazz == MacTokenAuthenticationToken.class;
  }

  /**
   * 未登陆的匿名用户
   * @return
   */
  private UserDetailInfo getAnonymous() {
    UserDetailInfo detail = new UserDetailInfo();
    User user = new User();
    user.setUserName("anonymous");
    detail.setUser(user);
    List<GrantedAuthority> authority = new ArrayList<>();
    authority.add(new UserGrantedAuthority());
    detail.setAuthorities(authority);
    detail.setAuthType("Mac");
    return detail;
  }

}
