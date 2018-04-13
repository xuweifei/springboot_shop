/**
 * @Title: PictureService.java 
 * @Package com.opengroup.longmao.gwcommon.service 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.service;

import java.util.List;

/**
 * @ClassName: PictureService 
 * @Description: 图片处理Service
 * @author Mr.Zhu
 */
public interface PictureService {
	
	/**
	 * @Title: savePicture 
	 * @Description: 保存图片信息 
	 * @param userId
	 * @param picType
	 * @param picUrl
	 */
	void savePicture(String userId, Integer picType, String picUrl);
	
	/**
	 * @Title: deleteToQiniu 
	 * @Description: 删除七牛文件 
	 * @param keyList
	 * @return void
	 */
	void deleteToQiniu(List<String> keyList);
}
