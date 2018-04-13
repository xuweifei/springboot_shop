package com.opengroup.longmao.gwcommon.tools.sms;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;

public class ReadXMLUtil {
	// 解析XML
	public static Map<String,Object> readStringXml(String xml) {
		Map<String,Object> map=new HashMap<>();
		Document doc = null;
		try {

			// 读取并解析XML文档
			// SAXReader就是一个管道，用一个流的方式，把xml文件读出来
			//
			// SAXReader reader = new SAXReader(); //User.hbm.xml表示你要解析的xml文档
			// Document document = reader.read(new File("User.hbm.xml"));
			// 下面的是通过解析xml字符串的
			doc = DocumentHelper.parseText(xml); // 将字符串转为XML

			Element rootElt = doc.getRootElement(); // 获取根节点
			System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称

			Iterator iter = rootElt.elementIterator("head"); // 获取根节点下的子节点head

			// 遍历head节点
			while (iter.hasNext()) {

				Element recordEle = (Element) iter.next();
				String error_code = recordEle.elementTextTrim("error_code"); // 拿到head节点下的子节点error_code值
				System.out.println("error_code:" + error_code);
				map.put("error_code", error_code);
				String error_msg = recordEle.elementTextTrim("error_msg"); // 拿到head节点下的子节点error_msg值
				System.out.println("error_msg:" + error_msg);
				map.put("error_msg", error_msg);
//				Iterator iters = recordEle.elementIterator("script"); // 获取子节点head下的子节点script
//
//				// 遍历Header节点下的Response节点
//				while (iters.hasNext()) {
//
//					Element itemEle = (Element) iters.next();
//
//					String username = itemEle.elementTextTrim("username"); // 拿到head下的子节点script下的字节点username的值
//					String password = itemEle.elementTextTrim("password");
//
//					System.out.println("username:" + username);
//					System.out.println("password:" + password);
//				}
			}
			Iterator iterss = rootElt.elementIterator("body"); /// 获取根节点下的子节点body
			// 遍历body节点
//			while (iterss.hasNext()) {
//
//				Element recordEless = (Element) iterss.next();
//				String result = recordEless.elementTextTrim("result"); // 拿到body节点下的子节点result值
//				System.out.println("result:" + result);
//
//				Iterator itersElIterator = recordEless.elementIterator("form"); // 获取子节点body下的子节点form
//				// 遍历Header节点下的Response节点
//				while (itersElIterator.hasNext()) {
//
//					Element itemEle = (Element) itersElIterator.next();
//
//					String banlce = itemEle.elementTextTrim("banlce"); // 拿到body下的子节点form下的字节点banlce的值
//					String subID = itemEle.elementTextTrim("subID");
//
//					System.out.println("banlce:" + banlce);
//					System.out.println("subID:" + subID);
//				}
//			}
		} catch (DocumentException e) {
			GwsLogger.error("读取xml数据失败:code={},message={},e={}", CommConstant.GWSCOD0014, CommConstant.GWSMSG0014,e.getMessage());

		} catch (Exception e) {
			GwsLogger.error("读取xml数据失败:code={},message={},e={}", CommConstant.GWSCOD0014, CommConstant.GWSMSG0014,e.getMessage());

		}
		return map;
	}
}
