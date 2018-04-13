/**
 * @Title: PictureController.java 
 * @Package com.opengroup.longmao.gwcommon.controller 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.configuration.properties.QiNiuConfig;
import com.opengroup.longmao.gwcommon.service.FileUploadService;
import com.opengroup.longmao.gwcommon.service.PictureService;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.RetResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName: PictureController
 * @Description: 图片控制类
 * @author Mr.Zhu
 */
@EnableSwagger2
@Api(value = "图片控制类", tags = "pic")
@RestController
@RequestMapping(value = { "/pic" })
public class PictureController {

	@Autowired
	private FileUploadService fileUploadService;

	@Autowired
	private PictureService pictureService;

	@Autowired
	private QiNiuConfig qiNiuConfig;

	@ApiOperation(value = "上传图片", notes = "上传图片")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "file", value = "七牛图片File", required = true, dataType = "MultipartFile"),
			@ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String"),
			@ApiImplicitParam(name = "photoType", value = "图片类型", required = true, dataType = "Integer") })
	@RequestMapping(value = "/uploadPic", method = RequestMethod.POST)
	public RetResult uploadPic(@RequestParam("userId") String userId, @RequestParam("photoType") Integer photoType,
			MultipartFile file) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("User服务(/pic/uploadPic):文件上传,开始:userId={},photoType={},startTime={}", userId, photoType, startTime);
		if (null == file || StringUtils.isBlank(userId)) {
			GwsLogger.error("参数不合法:code={},message={},userId={}", CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, userId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		GwsLogger.info("file大小为:fileSize={}", file.getSize());
		if (file.getSize() > Long.valueOf(qiNiuConfig.getQiniuFileMaxSize())||file.getSize()<=0) {
			GwsLogger.error("file大小不合法:fileSize={}", file.getSize());
			return RetResult.setRetDate(CommConstant.GWSCOD0003, "file大小不合法", null);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			map = fileUploadService.uploadToQiniu(file.getBytes(), file.getOriginalFilename());
			if (map.containsKey("downurl") && map.get("downurl").toString().contains("http")) {
				pictureService.savePicture(userId, photoType, map.get("downurl").toString());
				GwsLogger.info("文件上传,成功:code={},message={},map={},endTime={}毫秒", CommConstant.GWSCOD0000,
						CommConstant.GWSMSG0000, map, (System.currentTimeMillis() - startTime));
				return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, map);
			} else {
				GwsLogger.info("文件上传,失败:code={},message={},map={},endTime={}毫秒", CommConstant.GWSCOD0001,
						CommConstant.GWSMSG0001, map, (System.currentTimeMillis() - startTime));
				return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, null);
			}
		} catch (Exception e) {
			GwsLogger.error("文件上传,异常:code={},message={},userId={},photoType={},e={}", CommConstant.GWSCOD0001,
					CommConstant.GWSMSG0001, userId, photoType, e.getMessage());
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, null);
		}
	}
	
}
