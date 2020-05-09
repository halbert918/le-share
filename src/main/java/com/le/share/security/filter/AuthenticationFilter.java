package com.le.share.security.filter;

import com.le.share.security.exception.AuthenticationSecurityException;
import com.le.share.security.support.AuthenticationExtractorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yinbohe.
 * Date 2020/5/4
 * Description
 */
public class AuthenticationFilter extends OncePerRequestFilter implements InitializingBean {

  private Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

  private AuthenticationManager authenticationManager;

  private AuthenticationExtractorManager authenticationExtractorManager;

  public AuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationExtractorManager authenticationExtractorManager) {
    this.authenticationManager = authenticationManager;
    this.authenticationExtractorManager = authenticationExtractorManager;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

    String authentication = request.getHeader("Authentication");
    if (authentication == null) {
      logger.info("The authorization header is blank.");
      throw new AuthenticationSecurityException("The authorization header cannot be blank.");
    }

    Authentication accessAuthentication = authenticationExtractorManager.extractAuthentication(authentication);
    if (accessAuthentication == null) {
      logger.info("The authorization header is invalid, Cannnot support the authentication method.");
      response.addHeader("WWW-Authenticate", "error=\"invalid_token\" description=\"not support the authentication method\"");
      throw new AuthenticationSecurityException("The authorization header is invalid.");
    }

    try {
      // 认证
      Authentication fullyAuthentication = authenticationManager.authenticate(accessAuthentication);

      // 保障线程安全，Authentication提供给其他地方是用，
      // 比如@AuthenticationPrincipal可直接获取Authentication.getDetails()方法返回的值
      SecurityContextHolder.getContext().setAuthentication(fullyAuthentication);
    } catch (Exception ex) {
      logger.error("Authentication exception.", ex);
      response.addHeader("WWW-Authenticate", "error=\"invalid_token\" description=\"" + ex.getMessage() + "\"");
      throw new AuthenticationSecurityException("Authentication failed.");
    }

    chain.doFilter(request, response);

  }

  @Override
  public void afterPropertiesSet() throws ServletException {
    super.afterPropertiesSet();
    Assert.notNull(authenticationManager, "AuthenticationManager must be set");
    Assert.notNull(authenticationExtractorManager, "AuthenticationExtractorManager must be set");
  }



}
