/**
 * @Title: TopUpByHuYi.java 
 * @Package com.opengroup.longmao.gwcommon.tools.sdk.topup 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.tools.sdk.topup;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.enums.HuYiStatusCodeEnum;
import com.opengroup.longmao.gwcommon.tools.Md5Util;
import com.opengroup.longmao.gwcommon.tools.date.DateUtil;
import com.opengroup.longmao.gwcommon.tools.http.HttpRequest;

/**
 * @ClassName: TopUpByHuYi 
 * @Description: 话费充值接口-互亿无线
 * @author Mr.Zhu
 */
@Configuration
public class TopUpByHuYi {
	
	@Value("${ihuyi.topUp.HTTP}")
	private String ihuyiTopUpHttp;
	
	@Value("${ihuyi.topUp.apikey}")
	private String apiKey;
	
	@Value("${ihuyi.topUp.username}")
	private String userName;
	
	/**
	 * @Title: phoneFeeTopUpByHuYi 
	 * @Description: 话费充值接口-互亿无线 
	 * @param map(订单ID, 手机号码, 充值金额)
	 * @return JSONObject
	 */
	public Map<String, String> phoneFee(Map<String, String> map, String isTest) {
		//测试路线-返回数据
		if ("true".equals(isTest)) {
			Map<String, String> jsonMap = new HashMap<String, String>();
			jsonMap.put("code", "1");
			jsonMap.put("message", "话费充值测试成功");
			jsonMap.put("taskid", "Test_888888");
			GwsLogger.info("话费充值测试!");
			return jsonMap;
		}
		
		//正式路线-返回数据
		String mobile = map.get("mobile");
		String orderId = map.get("orderId");
		String price = map.get("price");
		String timestamp = DateUtil.getCurrentDateByFormat(DateUtil.DATA_PATTON_YYYYMMDDHHMMSS);
		try {
			StringBuffer sbf = new StringBuffer();
			sbf.append("apikey=" + apiKey.trim() + "&mobile=" + mobile + "&orderid=" + orderId + "");
			sbf.append("&package=" + price + "&timestamp=" + timestamp + "&username=" + userName.trim() + "");

			String sign = Md5Util.thirtyTwo(sbf.toString());// 32位MD5加密

			StringBuffer param = new StringBuffer();
			param.append(
					"action=recharge&username=" + userName.trim() + "&mobile=" + mobile + "&orderid=" + orderId + "");
			param.append("&package=" + price + "&timestamp=" + timestamp + "&sign=" + sign + "");

			//String content = HttpURLConnectionRequest.sendHttp(ihuyiTopUpHttp.trim(), param.toString(), "POST");
			String content = HttpRequest.httpPOST(ihuyiTopUpHttp.trim(), param.toString());
			JSONObject jsonObject = new JSONObject(content.toString());

			Map<String, String> jsonMap = new HashMap<String, String>();
			jsonMap.put("code", jsonObject.getString("code"));
			jsonMap.put("message", jsonObject.getString("message"));
			if ("1".equals(jsonMap.get("code"))) {
				jsonMap.put("taskid", jsonObject.getString("taskid"));
			}
			GwsLogger.info(HuYiStatusCodeEnum.getEnumByCode(jsonMap.get("code")).getExplain()
					+ ":orderId={},mobile={},price={},msg={}", orderId, mobile, price, jsonObject);
			return jsonMap;
		} catch (Exception e) {
			GwsLogger.error("话费充值提交失败:orderId={},mobile={},price={}", orderId, mobile, price);
		}
		return null;
	}
	
	
	/**
	 * @Title: parsePushNotify 
	 * @Description: 互亿无线推送结果-签名 
	 * @param request
	 * @return HuYiNotifyParams
	 */
	public HuYiNotifyParams parsePushNotify(HttpServletRequest request) throws Exception {
		String taskid = request.getParameter("taskid");
		String mobile = request.getParameter("mobile");
		String state = request.getParameter("state");
		String message = request.getParameter("message");
		
		StringBuffer sbf = new StringBuffer();
		sbf.append("apikey=" + apiKey.trim() + "&message=" + message + "&mobile="+mobile+"");
		sbf.append("&state=" + state + "&taskid=" + taskid + "");
		
		String sign = Md5Util.thirtyTwo(sbf.toString());// 32位MD5加密
		
		if (sign.equals(request.getParameter("sign"))) {
			HuYiNotifyParams params = new HuYiNotifyParams();
			params.setTaskId(taskid);
			params.setOrderID(request.getParameter("orderid"));
			params.setMobile(mobile);
			params.setState(state);
			params.setMessage(message);
			return params;
		}
		GwsLogger.error("互亿无线充值推送结果签名失败");
		return null;
	}
}
