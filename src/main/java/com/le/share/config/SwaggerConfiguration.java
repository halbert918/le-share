package com.le.share.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinbohe.
 * Date 2020/3/30
 * Description swagger文档.
 * http://127.0.0.1:8086/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

  @Value("${swagger.enable:true}")
  private boolean enableSwagger;

  @Value("${api.doc.path:com.le.share}")
  private String path;

  @Value("${api.doc.title: le-share api}")
  private String title;

  @Value("${api.doc.version:1.0.0}")
  private String version;

  @Value("${api.doc.description:le-share api doc}")
  private String description;

  /**
   * .
   *
   * @return
   */
  @Bean
  public Docket createRestApi() {
    ParameterBuilder tokenPar = new ParameterBuilder();
    List<Parameter> params = new ArrayList<>();
    tokenPar.name("ticket").description("用户令牌").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
    params.add(tokenPar.build());
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .enable(enableSwagger)
        .select()
        .apis(RequestHandlerSelectors.basePackage(path))
        .paths(PathSelectors.any())
        .build()
        .globalOperationParameters(params);
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title(title)
        .description(description)
        .version(version)
        .build();
  }
}
