package com.opengroup.longmao.gwcommon.configuration.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.opengroup.longmao.gwcommon.configuration.token.JwtUtil;
import com.opengroup.longmao.gwcommon.entity.po.User;
import com.opengroup.longmao.gwcommon.service.RedisReadWriteService;
import com.opengroup.longmao.gwcommon.service.UserService;
import com.opengroup.longmao.gwcommon.tools.result.ConfigConstant;

import io.jsonwebtoken.Claims;
/**
 * http 拦截器 demo
 *
 * @version
 * @author liuyi 2016年4月12日 下午1:35:11
 * 
 */
public class HttpTokenInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private RedisReadWriteService redis;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtUtil jwt;
	
	@Override
	public boolean preHandle(HttpServletRequest request ,HttpServletResponse response, Object handler) throws Exception {
//		if(Short.valueOf(cache.get(CommConstant.TURN_TOKEN, CommConstant.OFF_ON).toString()).equals(IsOrNotEnum.YES.getType())){
			String type = request.getHeader(ConfigConstant.TYPE);
			if (StringUtils.isEmpty(type)) {
				type = request.getParameter(ConfigConstant.TYPE);
			}
			if(StringUtils.isEmpty(type)){
				return true;
			}
			//外部调用
			if(type.equals("2")){
				String token = request.getHeader(ConfigConstant.TOKEN); // TOKEN名称
				String refreshtoken = request.getHeader(ConfigConstant.REFRESH_TOKEN); // TOKEN名称
				if (StringUtils.isEmpty(token)) {
					token = request.getParameter(ConfigConstant.TOKEN);
				}
				if (StringUtils.isEmpty(refreshtoken)) {
					refreshtoken = request.getParameter(ConfigConstant.REFRESH_TOKEN);
				}
				if(StringUtils.isEmpty(token)||StringUtils.isEmpty(refreshtoken)){
					return false;
				}
				//Object o = cache.get(CommConstant.JWT_TOKEN, token.trim());
				Object o = redis.get(ConfigConstant.JWT_TOKEN, token.trim());
				Claims claims = null;
				try{
					claims = jwt.parseJWT(token);
				}catch(Exception e){
					try{
						claims = jwt.parseJWT(refreshtoken);
					}catch(Exception e1){
						return false;
					}
				}
				String json = claims.getSubject();
				Map<String, Object> user = JSON.parseObject(json, Map.class);
				String subject = JwtUtil.generalSubject(user);
				token = jwt.createJWT(ConfigConstant.JWT_ID, subject, ConfigConstant.JWT_TTL);
				refreshtoken=jwt.createJWT(ConfigConstant.JWT_ID, subject, ConfigConstant.JWT_REFRESH_TTL);
				//cache.set(ConfigConstant.JWT_TOKEN, token.trim(), user, CommConstant.JWT_TTL/1000);
				redis.set(ConfigConstant.JWT_TOKEN, token.trim(), user, ConfigConstant.JWT_TTL/1000);
				response.addHeader(ConfigConstant.TOKEN, token);
				response.addHeader(ConfigConstant.REFRESH_TOKEN, refreshtoken);
				if(null==o){
					return false;
				}else{
					if(StringUtils.isEmpty(user.get("userId"))){
						return false;
					}else{
						User u = userService.queryUserByUserId(user.get("userId").toString());
						if(null==u){
							return false;
						}
						if(!u.getSig().equals(user.get("sig").toString())){
							return false;
						}
					}
					
				}
				return true;
			}else if(type.equals("1")){
				return true;
			}
			return false;
//		}
//		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
