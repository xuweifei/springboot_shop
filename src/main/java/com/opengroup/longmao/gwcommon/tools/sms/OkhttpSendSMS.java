package com.opengroup.longmao.gwcommon.tools.sms;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.configuration.properties.ApiConfig;
import com.opengroup.longmao.gwcommon.tools.crypto.MD5Util;
import com.opengroup.longmao.gwcommon.tools.date.DateUtil;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * 【发送短信】
 * @author YST
 *
 */
@Configuration
public class OkhttpSendSMS{	
	
	@Autowired
	private ApiConfig apiConfig;

	private static final MediaType CONTENT_TYPE = MediaType.parse("application/xml;charset=utf-8");

	private static OkHttpClient httpClient = new OkHttpClient();

	public  void sendThroughZhiCallHttp(String content, String mobile, String type,Long smsId) {
		String time=DateUtil.format(new Date(), "yyyyMMddHHmmss");
		StringBuffer sb = new StringBuffer();
		sb.append("app_key=" + apiConfig.getSms_app_key().trim())
		.append("&")
		.append("batch_num=" + apiConfig.getSms_nonce_str().trim() + smsId)
		.append("&")
		.append("content=【龙猫直播】" + content)
		.append("&")
		.append("dest_id=" + mobile)
		.append("&")
		.append("mission_num=" + smsId)
		.append("&")
		.append("nonce_str=" + apiConfig.getSms_nonce_str().trim())
		.append("&")
		.append("sms_type=" + type)
		.append("&")
		.append("time_stamp=" + time)
		.append("&")
		.append("app_secret=" + apiConfig.getSms_app_secret().trim());
		String sign = MD5Util.MD5(sb.toString());
		String xmlString = createXml(smsId, content, mobile, type, sign,time);
		RequestBody formBody = RequestBody.create(CONTENT_TYPE, xmlString);
		Request request = new Request.Builder().url(apiConfig.getSms_send_url().trim()).post(formBody).build();
		Response response = null;
		try {
			response = httpClient.newCall(request).execute();
		} catch (IOException e) {
			GwsLogger.error("短信发送失败:code={},message={},e={}", CommConstant.GWSCOD0012, CommConstant.GWSCOD0012,
					e.getMessage());
		}
		if (response.isSuccessful()) {
			String respnoseBody = null;
			try {
				respnoseBody = response.body().string();
				System.err.println(respnoseBody);
			} catch (IOException e) {
				GwsLogger.error("短信发送失败:code={},message={},e={}", CommConstant.GWSCOD0012, CommConstant.GWSCOD0012,
						e.getMessage());
			}
			Map<String,Object> map=ReadXMLUtil.readStringXml(respnoseBody);
			if (!map.get("error_code").equals("000000")) {
				GwsLogger.error("短信发送失败:code={},message={},e={}", CommConstant.GWSCOD0012, CommConstant.GWSCOD0012,
						map.get("error_msg"));
				return;
			}
		} else {
			GwsLogger.error("短信发送失败:code={},message={}", CommConstant.GWSCOD0012, CommConstant.GWSCOD0012);
		}

	}

	//生成XML
	private  String createXml(Long smsId, String content, String mobile, String type, String sign,String time) {
		String xmlString = "";

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			document.setXmlStandalone(true);

			Element xml = document.createElement("xml");
			document.appendChild(xml);

			Element head = document.createElement("head");
			// itemStatistics.setTextContent("商品统计");
			xml.appendChild(head);

			Element app_key = document.createElement("app_key");
			app_key.setTextContent(apiConfig.getSms_app_key().trim());
			head.appendChild(app_key);

			Element time_stamp = document.createElement("time_stamp");
			time_stamp.setTextContent(time);
			head.appendChild(time_stamp);

			Element nonce_str = document.createElement("nonce_str");
			nonce_str.setTextContent(apiConfig.getSms_nonce_str().trim());
			head.appendChild(nonce_str);

			Element signs = document.createElement("sign");
			signs.setTextContent(sign);
			head.appendChild(signs);
			// 此处可以循环添加
			Element body = document.createElement("body");
			xml.appendChild(body);

			Element dests = document.createElement("dests");
			body.appendChild(dests);

			// for(int i=0;i<2;i++){
			Element dest = document.createElement("dest");
			dests.appendChild(dest);

			Element mission_num = document.createElement("mission_num");
			mission_num.setTextContent(smsId+"");
			dest.appendChild(mission_num);

			Element dest_id = document.createElement("dest_id");
			dest_id.setTextContent(mobile);
			dest.appendChild(dest_id);
			// }

			Element batch_num = document.createElement("batch_num");
			batch_num.setTextContent(apiConfig.getSms_nonce_str().trim() + smsId);
			body.appendChild(batch_num);

			Element sms_type = document.createElement("sms_type");
			sms_type.setTextContent(type);
			body.appendChild(sms_type);

			Element contents = document.createElement("content");
			contents.setTextContent("【龙猫直播】"+content);
			body.appendChild(contents);

			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource domSource = new DOMSource(document);

			// xml transform String
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			transformer.transform(domSource, new StreamResult(bos));
			xmlString = bos.toString();
			System.err.println(xmlString);
		} catch (Exception e) {
			GwsLogger.error("生成xml数据失败:code={},message={},e={}", CommConstant.GWSCOD0013, CommConstant.GWSMSG0013,e.getMessage());
		}

		return xmlString;
	}
	
}
