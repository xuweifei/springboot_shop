/**
 *
 */
package com.opengroup.longmao.gwcommon.tools.idutil;

/**
 * 序列ID产生工具类
 *
 * @author sandy
 *
 */
public class IdUtil {
	private static final String DATE_FORMAT = "yyMMdd";

	/**
	 * 获取常用id
	 *
	 * @param sequenceValue
	 * @return
	 */
	public static String getCommonId(long sequenceValue) {
		return String.valueOf(sequenceValue);
	}

	/**
	 * 获取id的字符串形式
	 *
	 * @param sequenceValue
	 * @return
	 */
	public static String getStrId(long sequenceValue) {
		return String.valueOf(sequenceValue);
	}

	/**
	 * 获取A000 id
	 *
	 * @param sequenceValue
	 * @return
	 */
	public static String getA3Id(long sequenceValue) {
		StringBuilder id = new StringBuilder();
		id.append(String.valueOf(sequenceValue)).append("A000");
		return id.toString();
	}

	/**
	 * 获取业务id 共22位字符组成：yyyyMMdd+10位数字+A000
	 *
	 * @param sequenceValue
	 * @return
	 */
	public static String getAuctionId(long sequenceValue) {
		StringBuilder id = new StringBuilder();
		// 可以拼接业务标识符
		id.append("LM");
		id.append(StringUtil.fillLeft(String.valueOf(sequenceValue), 8, '0'));
		return id.toString();
	}
	/**
	 *生成短信验证码
	 * @param sequenceValue
	 * @return
	 */
	public static String getMessageCode(long sequenceValue) {
		StringBuilder id = new StringBuilder();
		id.append(StringUtil.fillLeft(String.valueOf(sequenceValue), 6, '0'));
		return id.toString();
	}

}
