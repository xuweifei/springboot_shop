/**
 * @Title: PictureServiceImpl.java 
 * @Package com.opengroup.longmao.gwcommon.service.impl 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.configuration.redis.cache.IdGlobalGenerator;
import com.opengroup.longmao.gwcommon.entity.po.PhotoAlbum;
import com.opengroup.longmao.gwcommon.repository.master.PhotoAlbumRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.slave.PhotoAlbumRepositorySlave;
import com.opengroup.longmao.gwcommon.service.PictureService;
import com.opengroup.longmao.gwcommon.tools.sdk.qiniu.QiniuUploadConfig;

/**
 * @ClassName: PictureServiceImpl 
 * @Description: TODO
 * @author Mr.Zhu
 */
@Service
public class PictureServiceImpl implements PictureService {
	
	@Autowired
	private PhotoAlbumRepositorySlave photoAlbumRepositorySlave;
	
	@Autowired
	private PhotoAlbumRepositoryMaster photoAlbumRepositoryMaster;
	
	@Autowired
	private QiniuUploadConfig qiniuUploadConfig;
	
	@Autowired
	private IdGlobalGenerator idGlobalGenerator;
	
	/**
	 * @Title: savePicture 
	 * @Description: 保存图片信息 
	 * @param userId
	 * @param picType
	 * @param picUrl
	 */
	@Override
	public void savePicture(String userId, Integer picType, String picUrl) {
		GwsLogger.info("保存图片:userId={},picType={},picUrl={}", userId, picType, picUrl);
		if (StringUtils.isNotBlank(picUrl)) {
			picUrl = picUrl.split("\\?")[0];
			String[] pic = picUrl.split("\\/");
			String picName = 0 < pic.length ? pic[pic.length - 1] : "";
			GwsLogger.info("分割后图片:userId={},picName={},picUrl={}", userId, picName, picUrl);
			try {
				// 未图片数据,创建新的图片数据
				Long id = idGlobalGenerator.getSeqId(PhotoAlbum.class);
				
				PhotoAlbum photo = new PhotoAlbum();
				photo.setId(id + "");
				photo.setUserId(userId);
				photo.setPhotoName(picName);
				photo.setPhotoType(picType);
				photo.setPhotoUrl(picUrl);
				photo.setGmtCreate(new Date());
				photo.setGmtCreateUser("QiNiu");
				photo.setGmtModified(new Date());
				photo.setGmtModifiedUser("QiNiu");
				photoAlbumRepositoryMaster.save(photo);
			} catch (Exception e) {
				GwsLogger.error("保存图片异常:userId={},picType={},picUrl={},e={}", userId, picType, picUrl, e);
			}
		}
	}
	
	/**
	 * @Title: getPhotoByUserIdOrType 
	 * @Description: 根据userId查询图片 
	 * @param userId
	 * @param picType
	 * @return PhotoAlbum
	 */
	public List<PhotoAlbum> getPhotoByUserIdOrType(String userId, Integer picType) {
		try {
			return photoAlbumRepositorySlave.getPhotoByUserIdOrType(userId, picType);
		} catch (Exception e) {
			GwsLogger.error("查询图片异常:userId={},picType={},e={}", userId, picType, e);
			return null;
		}
	}
	
	@Override
	public void deleteToQiniu(List<String> keyList) {
		if (CollectionUtils.isNotEmpty(keyList)) {
			try {
				for (String key : keyList) {
					qiniuUploadConfig.deleteFile(key);
					GwsLogger.info("删除七牛文件,完成:url={}", key);
				}
			} catch (Exception e) {
				GwsLogger.error("删除七牛文件,异常:keyList={},e={}", keyList, e);
			}
		}
	}

}
