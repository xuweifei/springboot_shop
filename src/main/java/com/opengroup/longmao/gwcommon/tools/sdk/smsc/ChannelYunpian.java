package com.opengroup.longmao.gwcommon.tools.sdk.smsc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.opengroup.longmao.gwcommon.tools.idutil.StringUtil;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.ImplException;

/**
 * @author sandy
 *
 */
public class ChannelYunpian {

	private String appkey;
	private String sendUrl;

	public ChannelYunpian(String appKey, String sendUrl) {
		this.appkey = appKey;
		this.sendUrl = sendUrl;
	}

	public boolean doSend(Map<String, Object> map) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", appkey);
		params.put("text", map.get("realContent").toString());
		params.put("mobile", map.get("mobile").toString());
		String respCode = post(sendUrl, params);
		JSONObject jasonObject = new JSONObject(respCode.toString());

		String code = jasonObject.getString("code");
		if (code.equals("0")) {
			return true;
		}
		return false;
	}

	/**
	 * 基于HttpClient 4.3的通用POST方法
	 *
	 * @param url
	 *            提交的URL
	 * @param paramsMap
	 *            提交<参数，值>Map
	 * @return 提交响应
	 */

	public static String post(String url, Map<String, String> paramsMap) {
		CloseableHttpClient client = HttpClients.createDefault();
		String responseText = "";
		CloseableHttpResponse response = null;
		try {
			HttpPost method = new HttpPost(url);
			if (paramsMap != null) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> param : paramsMap.entrySet()) {
					NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
					paramList.add(pair);
				}
				method.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
			}
			response = client.execute(method);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseText = EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return responseText;
	}
	
	public void init() {
		if (StringUtil.isBlank(appkey)) {
			throw new ImplException(CommConstant.GWSCOD0003, "未配置云片账号APIKEY");
		}
		if (StringUtil.isBlank(sendUrl)) {
			throw new ImplException(CommConstant.GWSCOD0003, "未配置云片网站URL");
		}
	}

}
