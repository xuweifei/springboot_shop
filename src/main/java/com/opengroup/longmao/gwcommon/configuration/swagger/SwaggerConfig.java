package com.opengroup.longmao.gwcommon.configuration.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;

import com.opengroup.longmao.gwcommon.configuration.properties.EnvironmentConfig;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Autowired
	private EnvironmentConfig environmentConfig;
	
	/**
	 * api
	 * @return
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).enable(Boolean.parseBoolean(environmentConfig.getSwagger_enable()))
	//      .groupName("api")
	        .genericModelSubstitutes(DeferredResult.class)
	        .useDefaultResponseMessages(false)
	        .forCodeGeneration(true)
	        .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
	        .select()
	        .apis(RequestHandlerSelectors.basePackage("com.opengroup.longmao.gwcommon.controller"))
	        .paths(PathSelectors.regex("/.*"))//过滤的接口
	//		.paths(or(regex("/.*")))//过滤的接口
	        .build()
	        .apiInfo(apiInfo());
		}
	
	/**
	 * apiInfo
	 * @return
	 */
	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
        .title("Totoro User Rest API")//大标题
        .description("龙猫运动用户中心服务 V1.8 REST API.")//详细描述
        .version("1.8")//版本
        .termsOfServiceUrl("NO terms of service http://blog.didispace.com/")
        .contact("Hermit")//作者
//      .contact(new Contact("Hermit", "http://blog.csdn.net/catoop", "85583755@qq.com"))//作者
        .license("The Apache License, Version 2.0")
        .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
        .build();
    }

}