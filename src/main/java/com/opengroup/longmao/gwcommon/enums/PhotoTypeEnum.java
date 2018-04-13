package com.opengroup.longmao.gwcommon.enums;

/**
 * @ClassName: PhotoTypeEnum
 * @Description: 相册
 * @author Mr.Zhu
 */
public enum PhotoTypeEnum {
	PHOTO_ALBUM(0, "相册"), 
	PHOTO_HEAD(1, "头像"), 
	PHOTO_LIVE(2, "直播图片"), 
	PHOTO_HOT(3, "热门banner"), 
	PHOTO_NEARBY(4, "附近banner");
	private Integer type;
	private String typeName;

	PhotoTypeEnum(Integer type, String typeName) {
		this.type = type;
		this.typeName = typeName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public static PhotoTypeEnum getEnumByType(Integer type) {
		if (type == null)
			return null;
		for (PhotoTypeEnum photoTypeEnum : PhotoTypeEnum.values()) {
			if (photoTypeEnum.getType().equals(type))
				return photoTypeEnum;
		}
		return null;
	}

	public static PhotoTypeEnum getEnumByName(String typeName) {
		if (typeName == null)
			return null;
		for (PhotoTypeEnum photoTypeEnum : PhotoTypeEnum.values()) {
			if (photoTypeEnum.getTypeName().equals(typeName))
				return photoTypeEnum;
		}
		return null;
	}

}
