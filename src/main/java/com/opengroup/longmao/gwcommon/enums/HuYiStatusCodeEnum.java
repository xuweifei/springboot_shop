/**
 * @Title: HuYiStatusCodeEnum.java 
 * @Package com.opengroup.longmao.gwcommon.enums 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.enums;

/**
 * @ClassName: HuYiStatusCodeEnum
 * @Description: 龙猫豆、龙猫币使用类型
 * @author Mr.Zhu
 */
public enum HuYiStatusCodeEnum {
	HUYI_CODE_0("0", "未知错误"), HUYI_CODE_1("1", "提交成功"), 
	HUYI_CODE_1000("1000", "无此操作类型(action为空或不存在)"), HUYI_CODE_1001("1001", "用户名为空"), 
	HUYI_CODE_1002("1002", "用户名错误"), HUYI_CODE_1003("1003", "手机号码为空"), 
	HUYI_CODE_1004("1004", "手机号码为空"), HUYI_CODE_1005("1005", "套餐不能为空"), 
	HUYI_CODE_1006("1006", "时间戳不能为空"), HUYI_CODE_1007("1007", "不存在的套餐"), 
	HUYI_CODE_1008("1008", "签名不能为空"), HUYI_CODE_1009("1009", "签名错误"), 
	HUYI_CODE_1010("1010", "签名过期"), HUYI_CODE_1011("1011", "账号被冻结"), 
	HUYI_CODE_1012("1012", "余额不足"), HUYI_CODE_1013("1013", "访问ip与备案ip不相同"), 
	HUYI_CODE_1014("1014", "订单ID不能为空"), HUYI_CODE_1015("1015", "订单ID已存在"),
	HUYI_CODE_2001("1010", "不支持的手机号码"), HUYI_CODE_2002("2002", "	手机号码已加入黑名单"), 
	HUYI_CODE_2003("2003", "不支持的地区"), HUYI_CODE_3001("3001", "扣费失败"), 
	HUYI_CODE_4001("4001", "系统内部故障");
	
	private String code;
	private String explain;

	private HuYiStatusCodeEnum(String code, String explain) {
		this.code = code;
		this.explain = explain;
	}
	
	public String getCode() {
		return code;
	}

	public String getExplain() {
		return explain;
	}
	
	public static HuYiStatusCodeEnum getEnumByCode(String code) {
		if (code == null)
			return null;
		for (HuYiStatusCodeEnum useTypeEnum : HuYiStatusCodeEnum.values()) {
			if (useTypeEnum.getCode().equals(code))
				return useTypeEnum;
		}
		return null;
	}

	public static HuYiStatusCodeEnum getEnumByExplain(String explain) {
		if (explain == null)
			return null;
		for (HuYiStatusCodeEnum useTypeEnum : HuYiStatusCodeEnum.values()) {
			if (useTypeEnum.getExplain().equals(explain))
				return useTypeEnum;
		}
		return null;
	}
}
