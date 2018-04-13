package com.opengroup.longmao.gwcommon.configuration.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 
 *【这里用一句话描述这个方法的作用】  
 * @author ShenZiYang 
 * @date 2018年4月10日 下午2:13:47
 */
@Configuration
public class TxUserConfig {

	/**
	 * 调用wap服务的更新用户扩展表接口
	 */
	@Value("${update.mall.wap.customer.interface.url}")
	private String mallCustomerExpand;
	
	
	public String getMallCustomerExpand() {
		return mallCustomerExpand;
	}

	public void setMallCustomerExpand(String mallCustomerExpand) {
		this.mallCustomerExpand = mallCustomerExpand;
	}
	

}
