/**
 * @Title: SmscYunPian.java 
 * @Package com.opengroup.longmao.gwcommon.tools.sdk.smsc 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.tools.sdk.smsc;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.opengroup.longmao.gwcommon.configuration.properties.ApiConfig;
import com.opengroup.longmao.gwcommon.service.RedisReadWriteService;
import com.opengroup.longmao.gwcommon.tools.RegExpTool;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.ImplException;

/**
 * @ClassName: SmscYunPian
 * @Description: 短信发送中心-云片
 * @author Mr.Zhu
 */
@Configuration
public class SmscYunPian {
	
	@Autowired
	private RedisReadWriteService redis;

	@Autowired
	private ApiConfig api;
	
	

	public Boolean doSend(List<Map<String, Object>> msgList) throws Exception {
		
		// 检查消息不能重复
		checkMsgsValid(msgList);
		ChannelYunpian yunPian = new ChannelYunpian(api.getSmscAppkey().trim(), api.getSendUrl().trim());
		yunPian.init();
		for (Map<String, Object> map : msgList) {
			String mobile = map.get("mobile").toString();
			try {
				map.put("appkey", api.getSmscAppkey().trim());
				map.put("sendUrl", api.getSendUrl().trim());
				return yunPian.doSend(map);
			} catch (ImplException e) {
				throw new ImplException(CommConstant.GWSCOD0001, "发送验证码短信失败!");
			} finally {
				//cache.set("msg_idx_", mobile, "", 86400L);
				redis.set("msg_idx_", mobile, "", 86400L);
			}
		}
		return false;
	}

	public void checkParams(List<Map<String, Object>> msgList) {
		for (Map<String, Object> map : msgList) {
			if (!map.containsKey("mobile")) {
				throw new ImplException(CommConstant.GWSCOD0003, "参数不合法, mobile必须不为空");
			}
			if (!RegExpTool.mobileMatch(map.get("mobile").toString())) {
				throw new ImplException(CommConstant.GWSCOD0003,
						"参数不合法, mobile[" + map.get("mobile").toString() + "]非法格式");
			}
		}
	}

	/**
	 * @Title: checkMsgsValid 
	 * @Description: 检查消息，如果消息重复， 将会发送失败 
	 * @param msgList
	 * @return void
	 */
	private void checkMsgsValid(List<Map<String, Object>> msgList) {
		String lastContent = "";
		for (Map<String, Object> map : msgList) {
			if (lastContent.equals(map.get("realContent").toString())) {
				throw new ImplException(CommConstant.GWSCOD0007, CommConstant.GWSMSG0007);
			}
			lastContent = map.get("realContent").toString();
		}
	}

}
