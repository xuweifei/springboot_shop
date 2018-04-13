package com.opengroup.longmao.gwcommon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;

@SpringBootApplication
@ServletComponentScan
@ImportResource({ "classpath:conf/redisConfig.xml" })
public class TotoroUserApplication {

	public static void main(String[] args) {
		SpringApplication app=new SpringApplication(TotoroUserApplication.class);
		app.setAddCommandLineProperties(false);//屏蔽命令行访问属性的设置
		app.run(args);
		GwsLogger.info("totoro user server is started!");
		System.out.println("totoro user server is started!");
	}
}
