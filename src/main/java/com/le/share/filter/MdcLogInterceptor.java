package com.le.share.filter;

import org.slf4j.MDC;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Created by yinbohe.
 * Date 2020/3/30
 * Description 增加MDC日志追踪.
 */
public class MdcLogInterceptor extends HandlerInterceptorAdapter {

  private static String REQUEST_MDC_ID = "mdcId";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    MDC.put(REQUEST_MDC_ID, UUID.randomUUID().toString().replace("-", ""));
    return super.preHandle(request, response, handler);
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response,
                         Object handler, ModelAndView modelAndView) throws Exception {
    super.postHandle(request, response, handler, modelAndView);
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                              Object handler, Exception ex) throws Exception {
    MDC.clear();
    super.afterCompletion(request, response, handler, ex);
  }

}
