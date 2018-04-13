package com.opengroup.longmao.gwcommon.configuration.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.configuration.redis.cache.CacheClient;
import com.opengroup.longmao.gwcommon.service.RedisReadWriteService;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.ConfigConstant;
import com.opengroup.longmao.gwcommon.tools.result.RetResult;
/**
 *  重复提交验证：
 * 	使用 token + 传入的参数 为key,使用redis缓存
 * 
 * @author hjy
 *
 * 2018年3月1日 下午3:18:04
 */
public class RepeatSubmission extends HandlerInterceptorAdapter{
	
	@Autowired
	private CacheClient redis;
	@Autowired
	private RedisReadWriteService storageRedis;
	//缓存时间
	private static final Long TIME = 1L;
	
	private static List<String> viewUrls = new ArrayList<String>();
	
	//不需要进行检验token的url
	static {
		viewUrls.add("/uic/quickLogin");
		viewUrls.add("/uic/pwdLogin");
		viewUrls.add("/uic/thirdPartyLogin");
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		String url=request.getRequestURI();
		if(!viewUrls.contains(url)){			
			//提交过来的token
//			String token = getToken(request,ConfigConstant.JWT_TOKEN); //获取请求体内的数据
			String token = request.getHeader("token");   //根据name,获取对应请求头数据
			
			Map userMap = storageRedis.get(ConfigConstant.JWT_TOKEN, token.trim());
			if(token!=null && !"".equals(token) && userMap != null && userMap.size()>0){//  && obj!=null && obj.equals(token)
				if(repeatDataValidator(request,token)){
					return handleInvalidToken(request,response);
				}else{
					return true;
				}
			}else{
				return inspectToken(request,response);
			}						
		}else{
			return true;
		}		
	}
	@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mode) throws Exception{
        
    }
	
	@Override
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}
	
	/** 
     * 验证同一个url数据是否相同提交  ,相同返回true 
     * @param httpServletRequest 
     * @return 
     */  
    public boolean repeatDataValidator(HttpServletRequest httpServletRequest,String token){   
        String params=JSONObject.toJSONString(httpServletRequest.getParameterMap());
        String url=httpServletRequest.getRequestURI();  
        Map<String,String> map=new HashMap<String,String>();  
        map.put(url, params);  
        String nowUrlParams=map.toString();
        nowUrlParams = token + ":" + nowUrlParams;
           
        Integer preUrlParams = redis.get(token, nowUrlParams);//提交的次数
        
        if(preUrlParams==null){//如果上一个数据为null,表示还没有访问页面  
        	redis.set(token, nowUrlParams, 1, TIME);//存入缓存
            return false; 
        }else{//否则，已经访问过页面  
            if(preUrlParams >= 1){
            	GwsLogger.error("token为："+token +"；url为："+url+"在【"+TIME+"】秒时间内，多次提交。");
                return true;  
            }else{
            	redis.set(token, nowUrlParams, 1, TIME);//存入缓存;
                return false;  
            } 
        }  
    } 
    
    
	/**
	 * 从请求域中获取给定token名字的token值
	 * 
	 */
	public static String getToken(HttpServletRequest request, String tokenName) {
		if (tokenName == null) {
			return null;
		}
		Map<?, ?> params = request.getParameterMap();		
		String[] tokens = (String[]) (String[]) params.get(tokenName);
		String token;
		if ((tokens == null) || (tokens.length < 1)) {
			GwsLogger.debug("body中未找到token name " + tokenName);
			return null;
		}
		token = tokens[0];
		return token;
	}
	
	/**
	 * 当出现重复提交时调用
	 */
	protected boolean handleInvalidToken(HttpServletRequest request, HttpServletResponse response)throws Exception {
		RetResult result = new RetResult();
		result.setCode(CommConstant.GWSCOD0041);
		result.setMessage(CommConstant.GWSMSG0041);
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(JSON.toJSON(result));
		} finally {
			response.getWriter().close();
		}
		return false;
	}
	/**
	 * 当token为空或者无效时调用
	 */
	protected boolean inspectToken(HttpServletRequest request, HttpServletResponse response)throws Exception {
		RetResult result = new RetResult();
		result.setCode(CommConstant.GWSCOD0040);
		result.setMessage(CommConstant.GWSMSG0040);
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(JSON.toJSON(result));
		} finally {
			response.getWriter().close();
		}
		return false;
	}
}
