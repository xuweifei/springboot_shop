package com.opengroup.longmao.gwcommon.configuration.query;

/**
 * 目前系统支持的where查询条件关键字
 *
 * @version 
 * @author zengjq  2016年10月26日 下午6:40:05
 * 
 */
public enum Where {
	in,
	like,
	equal,
	notEqual,//不等于
	lessThan,//小于
	lessThanOrEqualTo,//小于或等于
	greaterThan,//大于
	greaterThanOrEqualTo//大于或等于
}
