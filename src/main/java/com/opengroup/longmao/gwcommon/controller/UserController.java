package com.opengroup.longmao.gwcommon.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 【用户信息表】 控制类
 *
 * @version 1.0
 * @author Hermit 2017年03月15日 上午09:59:49
 */
@EnableSwagger2
@Api(value = "用户信息", tags = "uic")
@RestController
@RequestMapping(value = { "/uic" })
public class UserController {
	
	
}
