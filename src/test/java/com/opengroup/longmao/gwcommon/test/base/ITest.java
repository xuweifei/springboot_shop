package com.opengroup.longmao.gwcommon.test.base;

/**
 * 
 * 【单元测试框架】
 *
 * @version 
 * @author Hermit  2017年4月11日 下午5:38:52
 *
 */
public interface ITest {
	<T> T getDestBean(Class<T> className);
}
