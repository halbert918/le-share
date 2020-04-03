package com.le.share;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by yinbohe.
 * Date 2019/8/20
 * Description le-share启动入口.
 */
@SpringBootApplication(scanBasePackages = { "com.le.*" })
//@EnableAspectJAutoProxy
public class LeShareApplication {

  private static final Logger logger = LoggerFactory.getLogger(LeShareApplication.class);

  /**
   * .
   * @param args args
   */
  public static void main(String[] args) {
    SpringApplication.run(LeShareApplication.class, args);
  }

}
