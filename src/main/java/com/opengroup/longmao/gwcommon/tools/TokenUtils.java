package com.opengroup.longmao.gwcommon.tools;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.configuration.token.JwtUtil;
import com.opengroup.longmao.gwcommon.service.RedisReadWriteService;
import com.opengroup.longmao.gwcommon.tools.result.ConfigConstant;
/**
 * 生成TOKEN
 * @author hjy
 *
 * 2018年3月9日 下午2:12:18
 */
@Component
public class TokenUtils {
	@Autowired
	private RedisReadWriteService redis;
	@Autowired
	private JwtUtil jwt;
	
	/**
	 * 利用JWT和user,生成token
	 * @param userMap
	 * @return
	 */
	public String establishToken(Map<String, Object> userMap){
		String token = null;
		try {
			String subject = JwtUtil.generalSubject(userMap);
//			token = jwt.createJWT(ConfigConstant.JWT_ID, subject, ConfigConstant.JWT_TTL);
			token = UUID.randomUUID().toString().replaceAll("-", "");
			redis.set(ConfigConstant.JWT_TOKEN, token.trim(), userMap);			
		} catch (Exception e) {
			GwsLogger.error("生成token令牌失败：userMap={}", userMap);
		}
		return token;
	}
	/**
	 * 根据老token，生成新token
	 * @param oldToken
	 * @return
	 */
	public String updateRedisToken(String oldToken){
		if(oldToken!=null && !"".equals(oldToken)){
			Map userMap = redis.get(ConfigConstant.JWT_TOKEN, oldToken.trim());
			if(userMap != null && userMap.size()>0){
				redis.delete(ConfigConstant.JWT_TOKEN, oldToken.trim());
				return establishToken(userMap);
			}else{
				return null;
			}
		}else{
			return null;
		}
	}

}
