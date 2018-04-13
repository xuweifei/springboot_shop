package com.opengroup.longmao.gwcommon.configuration.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;  
import javax.servlet.FilterChain;  
import javax.servlet.FilterConfig;  
import javax.servlet.ServletException;   
import javax.servlet.ServletRequest;  
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.tools.TokenUtils;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.RetResult;  
   
  
  
/** 
 * 返回值输出过滤器
 * 这里可以：加密返回值 ；token更新；....
 *  
 *  @author hjy
 *
 * 2018年3月8日 下午3:25:04
 */ 
//@WebFilter(filterName="ResponseFilter", urlPatterns = {
//        "*"
//        })
public class ResponseFilter implements Filter{  
	@Autowired
	private TokenUtils tokenUtil;
  
   @Override  
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain){  
	    try {
			GwsLogger.info("Filter拦截请求开始");
			 HttpServletRequest req = (HttpServletRequest) request;
			String ole_token = req.getHeader("token");   //根据name,获取对应请求头数据
			System.out.println("old_token is :"+ole_token);
			ResponseWrapper wrapperResponse = new ResponseWrapper((HttpServletResponse)response);//转换成代理类  
			// 这里只拦截返回，直接让请求过去，如果在请求前有处理，可以在这里处理  
			filterChain.doFilter(request, wrapperResponse); 
			GwsLogger.info("Filter拦截回复开始");
			byte[] content = wrapperResponse.getContent();//获取返回值  
			String ciphertext = null;
			if (content.length > 0){  
			    String str = new String(content, "UTF-8"); 
		    	String token = tokenUtil.updateRedisToken(ole_token);
		    	
		    	Map<String, Object> mapTypes = JSON.parseObject(str);
		     	String extraData = (String) mapTypes.get("extraData");
		    	if(extraData==null || extraData.equals("")){
		    		mapTypes.put("extraData", token);
		    	}
		    	ciphertext = JSONObject.toJSONString(mapTypes);    
			}
			sendClient(response,ciphertext);
			GwsLogger.info("Filter拦截回复成功！");
		} catch (Exception e) {
			GwsLogger.debug("Filter报错，e={}",e); 
			GwsLogger.error("Filter报错，e={}",e); 
	    	RetResult result = new RetResult();
			result.setCode(CommConstant.GWSCOD0001);
			result.setMessage(CommConstant.GWSMSG0001);
			try {
				sendClient(response,JSON.toJSON(result).toString());
			} catch (IOException e1) {
				GwsLogger.debug("Filter报错，发送数据到客户端报错，e={}",e1); 
				GwsLogger.error("Filter报错，发送数据到客户端报错，e={}",e1); 
				e1.printStackTrace();
			}
		} 
    }  
  
    @Override  
    public void init(FilterConfig arg0)throws ServletException{  
  
    }  
  
    @Override  
    public void destroy(){  
  
    }  
    /**
     * 发送数据到客户端
     * @param response
     * @param str
     * @throws IOException
     */
    private static void sendClient(ServletResponse response,String str) throws IOException{
    	try {
			response.setCharacterEncoding("UTF-8");
			if(str!=null){
                response.setContentLength(str.getBytes("UTF-8").length);
            }
			response.getWriter().print(str);
		} finally {
			response.getWriter().close();
		}
    }
} 
