package com.opengroup.longmao.gwcommon.tools;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.dto.ESUserDTO;
import com.opengroup.longmao.gwcommon.entity.po.User;

public class EntityDtoConverter {
	/**
	 * 拷贝对象方法
	 */
	public static Object copy(Object objSource) throws Exception{
		// 获取源对象类型
		Class<?> clazz = objSource.getClass();
		// 获取源对象构造函数
		Constructor<?> construtctor = clazz.getConstructor();
		// 实例化出目标对象
		Object objDes = construtctor.newInstance();
		// 获得源对象所有属性
		Field[] fields = clazz.getDeclaredFields();
		// 遍历所有属性
		for (int i = 0; i < fields.length; i++) {
			// 属性对象
			Field field = fields[i];
			// 属性名
			String fieldName = field.getName();
			// 获取属性首字母
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			// 拼接get方法名如getName
			String getMethodName = "get" + firstLetter + fieldName.substring(1);
			// 得到get方法对象
			Method getMethod = clazz.getMethod(getMethodName);
			// 对源对象调用get方法获取属性值
			Object value = getMethod.invoke(objSource);

			// 拼接set方法名
			String setMethodName = "set" + firstLetter + fieldName.substring(1);
			// 获取set方法对象
			Method setMethod = clazz.getMethod(setMethodName,new Class[] { field.getType() });
			// 对目标对象调用set方法装入属性值
			setMethod.invoke(objDes, new Object[] { value });
		}
		return objDes;
	}

	public static ESUserDTO UserCopyToESUserDTO(User user){
		ESUserDTO g2 = new ESUserDTO();
		try {
			if(user != null){
				g2.setId(user.getId());
				g2.setUserId(user.getUserId());
				g2.setUserType(user.getUserType());
				g2.setUserState(user.getUserState());
				g2.setUserName(user.getUserName());
				g2.setWechatId(user.getWechatId());
				g2.setWechatName(user.getWechatName());
				g2.setQqId(user.getQqId());
				g2.setQqName(user.getQqName());
				g2.setWeiboId(user.getWechatId());
				g2.setWechatName(user.getWechatName());
				g2.setNickName(user.getNickName());
				g2.setPhoneId(user.getPhoneId());
				g2.setSex(user.getSex());
				g2.setGrade(user.getGrade());
				g2.setSigned(user.getSigned());
				g2.setCalorie(user.getCalorie());
				g2.setProfession(user.getProfession());
				g2.setHobbies(user.getHobbies());
				g2.setSports(user.getSports());
				g2.setGmtCreate(user.getGmtCreate());
				g2.setGmtCreateUser(user.getGmtCreateUser());
				g2.setGmtModified(user.getGmtModified());
				g2.setGmtModifiedUser(user.getGmtModifiedUser());
				g2.setExperience(user.getExperience());
				g2.setCity(user.getCity());
				g2.setPushId(user.getPushId());
				g2.setIsDelete(user.getIsDelete());
			}
		} catch (Exception e) {
			GwsLogger.info("ES复制实体类失败：userId={},e={}",user.getUserId(),e.getMessage());
		}
		return g2;
	}
	
//	public static void main(String[] args) throws Exception {
//		// 学生源对象
//		GoodsInfo g1 = new GoodsInfo();
//		g1.setName("30元");
//		g1.setGoodsTypeId(1L);
//		g1.setDiscount(98);
//		g1.setGameName("信天游");
//
//		// 复制学生对象
//		GoodsInfoDto g2 = (GoodsInfoDto) copy(g1);
//		System.out.println(g2.getGameName());
		
//		// 学生源对象
//		GoodsInfoDto g2 = new GoodsInfoDto();
//		g2.setGameName("信天游");
//		g2.setChlIdName("360");
//		g2.setChlName("baidu");
//
//		// 复制学生对象
//		GoodsInfo g1 = (GoodsInfo) copy(g2);
//		System.out.println(g1.getGameName());
//	}
}