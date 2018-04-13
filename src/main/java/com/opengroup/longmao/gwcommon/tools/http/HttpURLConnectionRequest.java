/**
 * @Title: HttpURLConnectionRequest.java 
 * @Package com.opengroup.longmao.gwcommon.tools.http 
 * @Description:
 * @author Administrator
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.tools.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ClassName: HttpURLConnectionRequest
 * @Description: TODO
 * @author Administrator
 */
public class HttpURLConnectionRequest {
	public static String sendHttp(String url, String param, String method) throws IOException {
		BufferedReader reader = null;
		InputStream is = null;
		StringBuffer sbf = new StringBuffer();
		try {
			if(!url.endsWith("?")){
				url+="?";
			}
			URL realUrl = new URL(url + param);
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			conn.setRequestMethod(method);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Charset", "UTF-8");
			conn.connect();
			is = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (is != null) {
				is.close();
			}
		}
		return sbf.toString();
	}
}
