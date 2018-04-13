/**
 * @Title: RedisReadWriteServiceImpl.java 
 * @Package org.spring.cloud.live.service.impl 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opengroup.longmao.gwcommon.configuration.redis.cache.CacheClient;
import com.opengroup.longmao.gwcommon.service.RedisReadWriteService;

/**
 * @ClassName: RedisReadWriteServiceImpl 
 * @Description: TODO
 * @author Mr.Zhu
 */
@Service
public class RedisReadWriteServiceImpl implements RedisReadWriteService {

	@Autowired
	private CacheClient cache;
	
	/**
	 * @Title: zSetSize 
	 * @Description: 获取sortedset数量 
	 * @param prefix
	 * @param key
	 * @return Integer
	 */
	@Override
	public <T> Integer zSetSize(String prefix, String key) {
		if (null == prefix || null == key) {
			return 0;
		}
		String realKey = prefix + key;
		return cache.zSetSize(realKey);
	}
	
	/**
	 * @Title: hashSize 
	 * @Description: 获取hashset数量 
	 * @param realKey
	 * @return Long
	 */
	@Override
	public <T> Long hashSize(String realKey) {
		if (null == realKey) {
			return 0L;
		}
		return cache.hashSize(realKey);
	}
	
	/**
	 * @Title: zSet 
	 * @Description: 设置sortedset值 
	 * @param prefix
	 * @param key
	 * @param t
	 * @param score
	 * @return boolean
	 */
	@Override
	public <T> boolean zSet(String prefix, String key, T t, double score) {
		if (key == null || prefix == null) {
			return false;
		}
		return cache.zSet(prefix, key, t, score);
	}
	
	/**
     * @Title: zGetDesc 
     * @Description: 读取sortedset值 
     * @param prefix
     * @param key
     * @param start
     * @param end
     * @return T
     */
	@Override
	public <T> T zGetDesc(String prefix, String key, Long start, Long end) {
		if (key == null || prefix == null) {
			return null;
		}
		return cache.zGetDesc(prefix, key, start, end);
	}
	
	/**
     * @Title: zGetAsc 
     * @Description: 读取sortedset值 
     * @param prefix
     * @param key
     * @param start
     * @param end
     * @return T
     */
	@Override
	public <T> T zGetAsc(String prefix, String key, Long start, Long end) {
		if (key == null || prefix == null) {
			return null;
		}
		return cache.zGetAsc(prefix, key, start, end);
	}
	
	/**
	 * @Title: zGetScore 
	 * @Description: 读取sortedset值 
	 * @param prefix
	 * @param key
	 * @param t
	 * @return T
	 */
	@Override
	public <T> T zGetScore(String prefix, String key, T t) {
		if (key == null || prefix == null) {
			return null;
		}
		return cache.zGetScore(prefix, key, t);
	}
	
	/**
	  * @Title: zRemove 
	  * @Description: 删除sortedset值 
	  * @param prefix
	  * @param key
	  * @param t
	  * @return Long
	  */
	@Override
	public <T> Long zRemove(String prefix, String key, T t) {
		if (key == null || prefix == null) {
			return 0L;
		}
		return cache.zRemove(prefix, key, t);
	}
	
	/**
	 * @Title: get 
	 * @Description: 读取Redis值
	 * @param prefix 后缀
	 * @param key 健
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String prefix, String key) {
		if (null == prefix || null == key) {
			return null;
		}
		return (T) cache.get(prefix, key);
	}
	
	/**
	 * @Title: set 
	 * @Description: 写入Redis值 
	 * @param prefix 后缀
	 * @param key 健
	 * @param t 写入值
	 * @return boolean
	 */
	@Override
	public <T> boolean set(String prefix, String key, T t) {
		if (null == prefix || null == key) {
			return false;
		}
		return cache.set(prefix, key, t);
	}
	
	/**
	 * @Title: set 
	 * @Description: 写入Redis值 
	 * @param prefix 后缀
	 * @param key 健
	 * @param t 写入值
	 * @param timeout 值失效时间
	 * @return boolean
	 */
	@Override
	public <T> boolean set(String prefix, String key, T t, Long timeout) {
		if (null == prefix || null == key) {
			return false;
		}
		return cache.set(prefix, key, t, timeout);
	}
	
	/**
	 * @Title: delete 
	 * @Description: 删除Redis值 
	 * @param prefix 后缀
	 * @param key 健
	 * @return boolean
	 */
	@Override
	public <T> boolean delete(String prefix, String key) {
		if (null == prefix || null == key) {
			return false;
		}
		cache.delete(prefix, key);
		return true;
	}
	
	/**
	 * @Title: getList 
	 * @Description: 读取Redis值-LIST 
	 * @param prefix 后缀
	 * @param key 健
	 * @return List<T>
	 */
	@Override
	public <T> List<T> getList(String prefix, String key) {
		if (null == prefix || null == key) {
			return null;
		}
		return cache.getList(prefix, key);
	}
	
	/**
	 * @Title: setList 
	 * @Description: 写入Redis值-LIST 
	 * @param prefix 后缀
	 * @param key 健
	 * @param t LIST
	 * @return boolean
	 */
	@Override
	public <T> boolean setList(String prefix, String key, List<T> t) {
		if (null == prefix || null == key) {
			return false;
		}
		return cache.setList(prefix, key, t);
	}
	
	/**
	 * @Title: hashGet 
	 * @Description: 读取Redis值-HASH 
	 * @param realKey 外层大健
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T hashGet(String realKey) {
		if (null == realKey) {
			return null;
		}
		return (T) cache.hashGet(realKey);
	}
	
	/**
	 * @Title: hashGet 
	 * @Description: 读取Redis值-HASH 
	 * @param realKey 外层大健
	 * @param hashKey 内层小健
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T hashGet(String realKey, String hashKey) {
		if (null == realKey || null == hashKey) {
			return null;
		}
		return (T) cache.hashGet(realKey, hashKey);
	}
	
	/**
	 * @Title: hashSet 
	 * @Description: 写入Redis值-HASH 
	 * @param realKey 外层大健
	 * @param hashKey 内层小健
	 * @param t
	 * @return boolean
	 */
	@Override
	public <T> boolean hashSet(String realKey, String hashKey, T t) {
		if (null == realKey || null == hashKey) {
			return false;
		}
		return cache.hashSet(realKey, hashKey, t);
	}
	
	/**
	 * @Title: hashDelete 
	 * @Description: 删除Redis值-HASH 
	 * @param realKey 外层大健
	 * @param hashKey 内层小健
	 * @return boolean
	 */
	@Override
	public <T> boolean hashDelete(String realKey, String hashKey) {
		if (null == realKey || null == hashKey) {
			return false;
		}
		return cache.hashDelete(realKey, hashKey);
	}

}
