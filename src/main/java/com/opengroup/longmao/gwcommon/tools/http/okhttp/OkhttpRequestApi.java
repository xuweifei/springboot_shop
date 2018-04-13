package com.opengroup.longmao.gwcommon.tools.http.okhttp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.ConfigConstant;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class OkhttpRequestApi {
	private final static int CONNECT_TIMEOUT = 1000;
	public final static int READ_TIMEOUT = 1000;
	public final static int WRITE_TIMEOUT = 2000;

	private static final MediaType CONTENT_TYPE = MediaType.parse("application/xml;charset=utf-8");

	private static OkHttpClient httpClient = new OkHttpClient();

	static {
		httpClient.setReadTimeout(READ_TIMEOUT, TimeUnit.SECONDS);// 设置读取超时时间
		httpClient.setWriteTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);// 设置写的超时时间
		httpClient.setConnectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);// 设置连接超时时;
	}

	public static String get(String url) {
		Request request = new Request.Builder().url(url.trim()).addHeader(ConfigConstant.TYPE, "1").get().build();
		Response response = null;
		String res = null;
		try {
			response = httpClient.newCall(request).execute();
			if (response.isSuccessful()) {
				res = response.body().string();
				GwsLogger.info("res={}:", res);
			} else {
				GwsLogger.error("http调用失败:code={},message={}", CommConstant.GWSCOD0001, CommConstant.GWSCOD0001);
			}
		} catch (IOException e) {
			GwsLogger.error("http调用失败:code={},message={},e={}", CommConstant.GWSCOD0001, CommConstant.GWSCOD0001,e.getMessage());
		} finally {
			if (response != null) {
				try {
					response.body().close();
				} catch (final Throwable th) {
					GwsLogger.error("response.body().close()关闭失败:code={},message={},e={}", CommConstant.GWSCOD0001,CommConstant.GWSCOD0001, th.getMessage());
				}
			}
		}
		return res;
	}

	public static boolean post(String url, String content) {
		boolean flag = false;
		RequestBody formBody = RequestBody.create(CONTENT_TYPE, content);
		Request request = new Request.Builder().url(url.trim()).addHeader(ConfigConstant.TYPE, "1").post(formBody).build();
		Response response = null;
		String res = null;
		try {
			response = httpClient.newCall(request).execute();
			if (response.isSuccessful()) {
				res = response.body().string();
				Map<String, Object> map = JSON.parseObject(res);
				if (map.get("ActionStatus").equals("OK") && map.get("ErrorCode").equals(Integer.valueOf(0))) {
					flag = true;
				}
			} else {
				GwsLogger.error("http调用失败:code={},message={}", CommConstant.GWSCOD0001, CommConstant.GWSCOD0001);
			}
		} catch (IOException e) {
			GwsLogger.error("http调用失败:code={},message={},e={}", CommConstant.GWSCOD0001, CommConstant.GWSCOD0001,e.getMessage());
		} finally {
			if (response != null) {
				try {
					response.body().close();
				} catch (final Throwable th) {
					GwsLogger.error("response.body().close()关闭失败:code={},message={},e={}", CommConstant.GWSCOD0001,CommConstant.GWSCOD0001, th.getMessage());
				}
			}
		}
		return flag;
	}

	public static Map<String, Object> httppost(String url, Param[] content) {
		Map<String, Object> map = new HashMap<String, Object>();
		// RequestBody formBody = RequestBody.create(CONTENT_TYPE, content);
		// Request request = new
		// Request.Builder().url(url.trim()).post(formBody).build();
		Request request = buildPostRequest(url, content);
		Response response = null;
		String res = null;
		try {
			response = httpClient.newCall(request).execute();
			if (response.isSuccessful()) {
				res = response.body().string();
				map = JSON.parseObject(res);
			} else {
				GwsLogger.error("okhttp调用失败:code={},message={}", CommConstant.GWSCOD0001, CommConstant.GWSCOD0001);
			}
		} catch (IOException e) {
			GwsLogger.error("okhttp调用失败:code={},message={},e={}", CommConstant.GWSCOD0001, CommConstant.GWSCOD0001,e.getMessage());
		} finally {
			if (response != null) {
				try {
					response.body().close();
				} catch (final Throwable th) {
					GwsLogger.error("response.body().close()关闭失败:code={},message={},e={}", CommConstant.GWSCOD0001,CommConstant.GWSCOD0001, th.getMessage());
				}
			}
		}
		return map;
	}

	public static Map<String, Object> httppostBackData(String url, Param[] content) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		// RequestBody formBody = RequestBody.create(CONTENT_TYPE, content);
		// Request request = new
		// Request.Builder().url(url.trim()).post(formBody).build();
		Request request = buildPostRequest(url, content);
		Response response = null;
		String res = null;
		try {
			response = httpClient.newCall(request).execute();
			if (response.isSuccessful()) {
				res = response.body().string();
				map = JSON.parseObject(res);
				if (CommConstant.GWSCOD0000.equals(map.get("code"))&& CommConstant.GWSMSG0000.equals(map.get("message"))) {
					data.put("data", map.get("data"));
					//data = (Map<String, Object>) map.get("data");
				}
			} else {
				GwsLogger.error("okhttp调用失败:code={},message={}", CommConstant.GWSCOD0001, CommConstant.GWSCOD0001);
			}
		} catch (IOException e) {
			GwsLogger.error("okhttp调用失败:code={},message={},e={}", CommConstant.GWSCOD0001, CommConstant.GWSCOD0001,e.getMessage());
		} finally {
			if (response != null) {
				try {
					response.body().close();
				} catch (final Throwable th) {
					GwsLogger.error("response.body().close()关闭失败:code={},message={},e={}", CommConstant.GWSCOD0001,CommConstant.GWSCOD0001, th.getMessage());
				}
			}
		}
		return data;
	}

	private static Request buildPostRequest(String url, Param[] params) {
		if (params == null) {
			params = new Param[0];
		}
		FormEncodingBuilder builder = new FormEncodingBuilder();
		for (Param param : params) {
			builder.add(param.key, param.value);
		}
		RequestBody requestBody = builder.build();
		return new Request.Builder().url(url).addHeader(ConfigConstant.TYPE, "1").post(requestBody).build();
	}

	public static class Param {
		private String key;
		private String value;

		public Param() {
		}

		public Param(String key, String value) {
			this.key = key;
			this.value = value;
		}
	}

	// public static void main(String[] args) {
	// Map<String, Object> paramsMap = new HashMap<String, Object>();
	// paramsMap.put("userId", 100035);
	// Param[] params =new Param[]{new Param("userId","100035")};
	// Map<String, Object> data =
	// httppost("http://testuic.tvlongmao.com/uic/anchorInfo", params);
	// GwsLogger.info("data={}",JSON.toJSONString(data));
	// }
}
