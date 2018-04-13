package com.opengroup.longmao.gwcommon.tools.sms;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegrexCheckUtil {
	/**
	 * 【手机正则校验】
	 * @param mobile
	 * @return
	 */
	public static boolean regex(String mobile){
		String regExp = "^1[3|4|5|7|8|9][0-9]{9}$";  
		Pattern p = Pattern.compile(regExp);  
		Matcher m = p.matcher(mobile.replace("+86", ""));  
		return m.find();//boolean  
	}
}
