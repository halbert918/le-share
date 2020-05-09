package com.le.share.security.support;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Created by yinbohe.
 * Date 2020/5/4
 * Description
 */
public interface AuthenticationExtractor {

  /**
   * 根据authentication参数构建Authentication.
   * @param authentication
   * @return Authentication
   * @throws AuthenticationException
   */
  Authentication extractAuthentication(String authentication) throws AuthenticationException;

  /**
   * 根据authentication请求参数规范判断是否支持认证模式.
   * @return boolean
   */
  boolean supports(String authentication);

}
