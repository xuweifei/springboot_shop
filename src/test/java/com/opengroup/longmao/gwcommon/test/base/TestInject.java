package com.opengroup.longmao.gwcommon.test.base;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;


/**
 * 
 * 【测试框架bean注入注解】
 *
 * @version 
 * @author Hermit  2017年4月11日 下午5:39:23
 *
 */
@Target({java.lang.annotation.ElementType.CONSTRUCTOR, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestInject {
	boolean required() default true;
}
