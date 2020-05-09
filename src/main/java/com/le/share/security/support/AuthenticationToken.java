package com.le.share.security.support;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import javax.security.auth.Subject;

/**
 * Created by yinbohe.
 * Date 2020/5/4
 * Description
 */
public class AuthenticationToken extends AbstractAuthenticationToken {

  private Object token;

  public AuthenticationToken(Object token, UserDetailInfo userDetail) {
    super(userDetail.getAuthorities());
    this.token = token;
    setDetails(userDetail);
    setAuthenticated(true);
  }

  /**
   * 凭证
   * @return
   */
  @Override
  public Object getCredentials() {
    return token;
  }

  /**
   * 获取用户ID.
   * @AuthenticationPrincipal 注解可获取返回值
   * @return
   */
  @Override
  public Object getPrincipal() {
    UserDetailInfo detail = (UserDetailInfo) super.getDetails();
    return detail.getUser();
  }

  @Override
  public boolean implies(Subject subject) {
    if (subject == null)
      return false;
    return subject.getPrincipals().contains(this);
  }
}
