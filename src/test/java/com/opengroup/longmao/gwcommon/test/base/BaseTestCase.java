package com.opengroup.longmao.gwcommon.test.base;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.junit.Before;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import com.opengroup.longmao.gwcommon.TotoroUserApplication;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;

import junit.framework.TestCase;

/**
 * 
 * 【框架单元测试基础类】
 *
 * @version 
 * @author Hermit  2017年4月11日 下午5:37:41
 *
 */
public class BaseTestCase extends TestCase implements ITest {
	private static ApplicationContext ctx;
	
	@Before
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		String[] args = {};
        ctx = SpringApplication.run(TotoroUserApplication.class, args);
        autoInjectObj();
        
	}
    
    /**
     * 
     * 自动注入对象
     * 
     * @author Hermit 2016年6月24日
     */
	private void autoInjectObj(){
    	Field[] fields = getClass().getDeclaredFields();
		for(Field field:fields){
			Annotation anno = field.getAnnotation(TestInject.class);
			if(anno!=null){
				try {
					field.setAccessible(true);
					field.set(this, getDestBean(field.getType()));
				} catch (Exception e) {
					GwsLogger.error(e.getMessage());
				}
			}
		}
    }



	/**
	 * 【请在此输入描述文字】
	 * 
	 * (non-Javadoc)
	 * @return 
	 * @see com.opengroup.longmao.gwcommon.test.base.ITest#getDestBean(java.lang.Class)
	 */
	@Override
	public <T> T getDestBean(Class<T> className) {
		T obj =null;
		if(ctx!=null){
			obj = ctx.getBean(className);
		}
		return obj;
	}
    
    

}
