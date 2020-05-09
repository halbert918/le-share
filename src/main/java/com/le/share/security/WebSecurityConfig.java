package com.le.share.security;

import com.le.share.security.filter.AuthenticationFilter;
import com.le.share.security.support.AuthenticationExtractorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

import java.util.List;

/**
 * Created by yinbohe.
 * Date 2020/4/14
 * Description
 */
@Configurable
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired(required = false)
  private List<AuthenticationProvider> providers;

  @Autowired
  public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
    if (providers != null) {
      for (AuthenticationProvider provider : providers) {
        auth.authenticationProvider(provider);
      }
    }
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    super.configure(http);
    http.authorizeRequests()
//        .anyRequest().permitAll()
//        .anyRequest().authenticated()                  //允许认证通过的用户访问
        .and()
        .addFilterAfter(authenticationFilter(),
            SecurityContextPersistenceFilter.class)   //保障SecurityContext内的对象被卸载
        .csrf().disable()
        .cors();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    super.configure(web);
    web.ignoring()
        .antMatchers("/logs")
        .antMatchers("/health")
        .antMatchers("/user/login");
//        .antMatchers(HttpMethod.GET,"/api/**");
  }

  /**
   * 创建AuthenticationFilter.
   * @return
   * @throws Exception
   */
  private AuthenticationFilter authenticationFilter() throws Exception {
    return new AuthenticationFilter(super.authenticationManager(), authenticationExtractorManager());
  }

  @Bean
  public AuthenticationExtractorManager authenticationExtractorManager() {
    return new AuthenticationExtractorManager();
  }

}
