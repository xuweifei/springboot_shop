/**
 * @Title: IsPlusEnum.java 
 * @Package com.opengroup.longmao.gwcommon.enums 
 * @Description:
 * @author Administrator
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.enums;

/**
 * @ClassName: IsPlusEnum
 * @Description: 是否增加
 * @author Administrator
 */
public enum IsPlusEnum {
	PLUS(0, "增加"), 
	MINUS(1, "减少");
	private Integer val;
	private String code;

	private IsPlusEnum(Integer val, String code) {
		this.val = val;
		this.code = code;
	}

	public Integer getVal() {
		return val;
	}

	public String getCode() {
		return code;
	}

	public static IsPlusEnum getEnumByVal(Integer val) {
		if (val == null)
			return null;
		for (IsPlusEnum isPlusEnum : IsPlusEnum.values()) {
			if (isPlusEnum.getVal().equals(val))
				return isPlusEnum;
		}
		return null;
	}

	public static IsPlusEnum getEnumByCode(String code) {
		if (code == null)
			return null;
		for (IsPlusEnum isPlusEnum : IsPlusEnum.values()) {
			if (isPlusEnum.getCode().equals(code))
				return isPlusEnum;
		}
		return null;
	}
}
