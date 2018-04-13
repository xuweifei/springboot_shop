package com.opengroup.longmao.gwcommon.configuration.interceptor;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.configuration.redis.cache.CacheClient;
import com.opengroup.longmao.gwcommon.tools.result.RetResult;
/**
 * 用法：
 * 1、将此类加入TokenInterceptor拦截器类
 * 2、然后在页面获取token代码:<input type="hidden" name="submit_again_token" th:value="${#request.getAttribute('submit_again_token')}" />
 * 3、在TokenInterceptor类中增加需要拦截的路径(生产token)和可能回产生重复提交的路径(比较redis中的token),例如拦截进入注册的页面路基和注册提交路径
 * @see TokenHelper
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

	private static Map<String, String> viewUrls = new HashMap<String, String>();
	private static Map<String, String> actionUrls = new HashMap<String, String>();
	private Object clock = new Object();
	
	/**
	 * 保存token值的默认命名空间
	 */
	public static final String TOKEN_NAMESPACE = "submit_again_tokens";

	/**
	 * 持有token名称的字段名
	 */
	public static final String TOKEN_NAME_FIELD = "submit_again_token";
	private static final Random RANDOM = new Random();

	@Autowired
	private CacheClient redisCacheClient;

	//需要进行重复提交检验的url
	static {
		//get地址
//		viewUrls.put("/user/selectUser", "GET");
//		viewUrls.put("/user/insertUser", "GET");
//		viewUrls.put("/user/deleteUser", "GET");
//		viewUrls.put("/user/updateUser", "GET");
//		viewUrls.put("/index", "GET");

		//post地址
//		actionUrls.put("/user/selectUser", "POST");
//		actionUrls.put("/user/saveUser", "POST");
//		actionUrls.put("/user/deleteUser", "POST");
//		actionUrls.put("/user/updateUser", "POST");
//		actionUrls.put("/uic/quickLogin", "POST");
	}
//	{
//		TokenHelper.setRedisCacheClient(redisCacheClient);
//	}

	/**
	 * 拦截方法，添加or验证token
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		
		String url = request.getRequestURI();
		String method = request.getMethod();
		//get方式
		if (viewUrls.keySet().contains(url) && ((viewUrls.get(url)) == null || viewUrls.get(url).equals(method))) {
			GwsLogger.info("get拦截调用以检查有效事务令牌。");
			setToken(request);
			return true;
	    //post方式
		} else if (actionUrls.keySet().contains(url)&& ((actionUrls.get(url)) == null || actionUrls.get(url).equals(method))) {
			GwsLogger.info("post拦截调用以检查有效事务令牌。");
			return handleToken(request, response, handler);
		}
		return true;
	}

	protected boolean handleToken(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		synchronized (clock) {
			//出现非法令牌
			if (!validToken(request)) {
				GwsLogger.info("未通过验证，不允许通行...");
				return handleInvalidToken(request, response, handler);
			}
		}
		//出现合法令牌
		GwsLogger.info("通过验证,允许通行...");
		return handleValidToken(request, response, handler);
	}

	/**
	 * 当出现一个非法令牌时调用
	 */
	protected boolean handleInvalidToken(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		RetResult result = new RetResult();
		result.setCode("100900");
		result.setMessage("check fail,please do not submit again!");
		result.setData("fasle");
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(JSON.toJSON(result));
		} finally {
			response.getWriter().close();
		}
		return false;
	}

	/**
	 * 当发现一个合法令牌时调用.
	 */
	protected boolean handleValidToken(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		return true;
	}

	/**
	 * 使用给定的字串作为token名字保存token
	 * 
	 * @param request
	 * @param tokenName
	 * @return token
	 */
	private String setToken(HttpServletRequest request) {
		//使用随机字串作为token名字保存token
		String tokenName = generateGUID();
		String tokenValue = generateGUID();
		setCacheToken(request, tokenName, tokenValue);
		return tokenValue;
	}

	/**
	 * 保存一个给定名字和值的token
	 * 
	 * @param request
	 * @param tokenName
	 * @param token
	 */
	private void setCacheToken(HttpServletRequest request, String tokenName, String tokenValue) {
		try {
			//组装一个带有命名空间为前缀的token名字
			String totoro_tokens = buildTokenCacheAttributeName(tokenName);
			//效果：key=totoro_tokens:A5M4NHZOX99KJ69685689OF5MWKUDBS0,value=E50C5NT6RWG4WIBT28ORNI8HYOEW3IEW
			redisCacheClient.push(totoro_tokens, tokenValue);
			GwsLogger.info("缓存到redis的token键值对：key={},value={}", totoro_tokens,tokenValue);
			
//			request.setAttribute(TOKEN_NAME_FIELD, tokenName);
//			request.setAttribute("tokenValue", tokenValue);
//			request.setAttribute(tokenName, tokenValue);
			
			//返回给客户端的植，效果：key=token,value=A5M4NHZOX99KJ69685689OF5MWKUDBS0_E50C5NT6RWG4WIBT28ORNI8HYOEW3IEW
			request.setAttribute("submit_again_token", tokenName+"_"+tokenValue);
			GwsLogger.info("返回给客户端的token键值对：key={},value={}", "token",tokenName+"_"+tokenValue);
			
		} catch (IllegalStateException e) {
			String msg = "由于客户响应问题导致创建HttpSession失败。你可以使用createsessioninterceptor或从创造HttpSession把结果呈现给客户端: "+ e.getMessage();
			GwsLogger.error(msg, e);
			throw new IllegalArgumentException(msg);
		}
	}

	/**
	 * 构建一个基于token名字的带有命名空间为前缀的token名字
	 * 
	 * @param tokenName
	 * @return the name space prefixed session token name
	 */
	public static String buildTokenCacheAttributeName(String tokenName) {
		return TOKEN_NAMESPACE + ":" + tokenName;
	}

	/**
	 * 从请求域中获取给定token名字的token值
	 * 
	 * @param tokenName
	 * @return the token String or null, if the token could not be found
	 */
	public static String getToken(HttpServletRequest request, String tokenName) {
		if (tokenName == null) {
			return null;
		}
		Map<?, ?> params = request.getParameterMap();
		String[] tokens = (String[]) (String[]) params.get(tokenName);
		String token;
		if ((tokens == null) || (tokens.length < 1)) {
			GwsLogger.debug("未找到token name " + tokenName);
			return null;
		}
		token = tokens[0];
		return token;
	}

	/**
	 * 从请求参数中获取token名字
	 * 
	 * @return the token name found in the params, or null if it could not be found
	 */
	public static String getTokenName(HttpServletRequest request) {
		Map<String, String[]> params = request.getParameterMap();

		if (!params.containsKey(TOKEN_NAME_FIELD)) {
			GwsLogger.debug("在request参数中未找到需要的token name：tokenName={}",TOKEN_NAME_FIELD);
			return null;
		}
		String[] tokenNames = (String[]) params.get(TOKEN_NAME_FIELD);
		String tokenName;

		if ((tokenNames == null) || (tokenNames.length < 1)) {
			GwsLogger.debug("得到了一个null或者empty的token name.");
			return null;
		}
		tokenName = tokenNames[0];
		return tokenName;
	}

	/**
	 * 验证当前请求参数中的token是否合法，如果合法的token出现就会删除它，它不会再次成功合法的token
	 * 
	 * @return 验证结果
	 */
	public boolean validToken(HttpServletRequest request) {
//		String tokenName = getTokenName(request);
//
//		if (tokenName == null) {
//			GwsLogger.debug("no token name found -> Invalid token ");
//			return false;
//		}
//
//		String tokenValue = getToken(request, tokenName);
//
//		if (tokenValue == null) {
//			// if(LOG.isDebugEnabled()){
//			GwsLogger.debug("no token found for token name " + tokenName + " -> Invalid token ");
//			// }
//			return false;
//		}
//
//		String cacheTokenName = buildTokenCacheAttributeName(tokenName);
//		String cacheTokenValue = redisCacheClient.pop(cacheTokenName);
//
//		if (!tokenValue.equals(cacheTokenValue)) {
//			GwsLogger.debug("internal.invalid.token Form token " + tokenValue + " does not match the session token "+ cacheTokenValue + ".");
//			return false;
//		}
//		// remove the token so it won't be used again
//		return true;
		
		String key_value = getTokenName(request);
		GwsLogger.info("得到key_value：key_value={}",key_value);
		
		if (key_value == null) {
			GwsLogger.debug("没发现 token key和token value-> 无效的token");
			return false;
		}
		
		String[] kvs  = key_value.split("_");
	    if (kvs == null || kvs.length < 2) {
			GwsLogger.error("kvs长度不正确：kvs.length={}",kvs.length);
			return false;
		}
		
		String cacheTokenName = buildTokenCacheAttributeName(kvs[0]);
		//leftPop出参，本无须remove
		String cacheTokenValue = redisCacheClient.pop(cacheTokenName);
		//还是删除下吧，感觉redis会滞留很多东西
		redisCacheClient.delete("", cacheTokenName);
		GwsLogger.info(cacheTokenValue);
		if (!kvs[1].equals(cacheTokenValue)) {
			GwsLogger.debug("客户端token和redis缓存中的token匹配无效：redis中的token={},客户端中的token={}",kvs[1],cacheTokenValue);
			return false;
	    }
		return true;
	}

	public static String generateGUID() {
		return new BigInteger(165, RANDOM).toString(36).toUpperCase();
	}

}