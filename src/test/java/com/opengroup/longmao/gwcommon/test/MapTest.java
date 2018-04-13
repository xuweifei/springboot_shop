package com.opengroup.longmao.gwcommon.test;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.opengroup.longmao.gwcommon.test.base.BaseTestCase;
import com.opengroup.longmao.gwcommon.tools.http.HttpRequestApi;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;

public class MapTest extends BaseTestCase {

	public void testSendMessage() {
		try {
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("userId", "10000002");
			paramsMap.put("guessId", "1493280559197039");
			Map<String, Object> map = HttpRequestApi.httpPost(paramsMap,
					"http://tv.tvlongmao.com/guessInfo/getBalanceResult", null);
			if (map.get("resultCode").equals(CommConstant.GWSSUCC)) {
				JSONObject jsonRsp = JSONObject.parseObject(map.get("strResponse").toString());
				String retCode = jsonRsp.getString("code");
				String data = jsonRsp.getString("data");
				if (CommConstant.GWSCOD0000.equals(retCode)) {

					JSONObject jsonject = JSONObject.parseObject(data);
					
					System.out.println("robProfit:"+jsonject.getDouble("robProfit"));
					
					for (Map.Entry<String, Object> entry : jsonject.entrySet()) {
			            System.out.println(entry.getKey() + ":" + entry.getValue());
			        }

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
