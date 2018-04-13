/**
 * @Title: FollowStateEnum.java 
 * @Package com.opengroup.longmao.gwcommon.domain.constants.enums 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.enums;

/**
 * @ClassName: FollowStateEnum
 * @Description: TODO
 * @author Mr.Zhu
 */
public enum FollowStateEnum {
	FOLLOW(Short.valueOf("1"), "关注"), 
	UNFOLLOW(Short.valueOf("2"), "取关"),
	;
	private Short val;
	private String code;

	private FollowStateEnum(Short val, String code) {
		this.val = val;
		this.code = code;
	}

	public Short getVal() {
		return val;
	}

	public String getCode() {
		return code;
	}

	public static FollowStateEnum getEnumByVal(Short val) {
		if (val == null)
			return null;
		for (FollowStateEnum followStateEnum : FollowStateEnum.values()) {
			if (followStateEnum.getVal().equals(val))
				return followStateEnum;
		}
		return null;
	}

	public static FollowStateEnum getEnumByCode(String code) {
		if (code == null)
			return null;
		for (FollowStateEnum followStateEnum : FollowStateEnum.values()) {
			if (followStateEnum.getCode().equals(code))
				return followStateEnum;
		}
		return null;
	}
}
