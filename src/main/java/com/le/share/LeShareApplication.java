package com.le.share;

import org.mybatis.generator.api.ShellRunner;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by yinbohe.
 * Date 2020/4/30
 * Description le-share启动入口.
 */
@SpringBootApplication(scanBasePackages = { "com.le.*" })
//@EnableAspectJAutoProxy
@MapperScan("com.le.share.mapper")
public class LeShareApplication {

  private static final Logger logger = LoggerFactory.getLogger(LeShareApplication.class);

  /**
   * .
   * @param args args
   */
  public static void main(String[] args) {

    if (args != null && args.length > 0) {
      args = new String[] {"-configfile", "src\\main\\resources\\mybatis-generator-config.xml", "-overwrite"};
      logger.info("Init mybatis mapper xml and entity.");
      ShellRunner.main(args);
    }

//    args = new String[] {"-configfile", "src/main/resources/mybatis-generator-config.xml", "-overwrite"};
//
//    logger.info("Init mybatis mapper xml and entity.");
//    ShellRunner.main(args);

    SpringApplication.run(LeShareApplication.class, args);
  }

}
