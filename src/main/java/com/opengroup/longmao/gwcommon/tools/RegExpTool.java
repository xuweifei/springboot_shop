package com.opengroup.longmao.gwcommon.tools;

import java.util.regex.Pattern;

/**
 * 正则表达式
 * 
 * @author sandy
 */
public class RegExpTool {

	private static String pwdReg = "^[/!/@/#/$/%/_A-Za-z0-9]{6,16}$";

	private static String mobileReg = "^1[0-9]{10}$";

	private static String emailReg = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

	/**
	 * 密码是否满足规则的
	 * 
	 * @param txt
	 * @return
	 */
	public static boolean pwdMatch(String txt) {
		return Pattern.matches(pwdReg, txt);
	}

	/**
	 * 手机号是否满足
	 * 
	 * @param txt
	 * @return
	 */
	public static boolean mobileMatch(String txt) {
		return Pattern.matches(mobileReg, txt);
	}

	/**
	 * 
	 * 邮箱是否满足
	 * 
	 * @param txt
	 * @return
	 */
	public static boolean emailMatch(String txt) {
		return Pattern.matches(emailReg, txt);
	}
}
