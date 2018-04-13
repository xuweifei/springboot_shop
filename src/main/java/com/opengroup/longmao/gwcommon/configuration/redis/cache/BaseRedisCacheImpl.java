package com.opengroup.longmao.gwcommon.configuration.redis.cache;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.ListOperations;

/**
 * 
 * REDIS缓存实现类
 *
 * @version 
 * @author liuyi  2016年4月25日 下午1:44:12
 *
 */
public class BaseRedisCacheImpl extends AbstractBaseRedis implements CacheClient {
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Integer zSetSize(String realKey) {
		if (null == realKey) {
			return 0;
		}
		return redisTemplate.opsForZSet().size(realKey).intValue();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Long hashSize(String realKey) {
		if (null == realKey) {
			return 0L;
		}
		return redisTemplate.opsForHash().size(realKey);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> boolean zSet(String prefix, String key, T t, double score) {
		if (key == null || prefix == null) {
			return false;
		}
		String realKey = prefix + key;
		redisTemplate.opsForZSet().add(realKey, t, score);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T zGetDesc(String prefix, String key, Long start, Long end) {
		if (key == null || prefix == null) {
			return null;
		}
		String realKey = prefix + key;
		return (T) redisTemplate.opsForZSet().reverseRange(realKey, start, end);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T zGetAsc(String prefix, String key, Long start, Long end) {
		if (key == null || prefix == null) {
			return null;
		}
		String realKey = prefix + key;
		return (T) redisTemplate.opsForZSet().range(realKey, start, end);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T zGetScore(String prefix, String key, T t) {
		if (null == prefix || null == key) {
			return null;
		}
		prefix = prefix + key;
		return (T) redisTemplate.opsForZSet().score(prefix, t);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Long zRemove(String prefix, String key, T t) {
		if (key == null || prefix == null) {
			return 0L;
		}
		String realKey = prefix + key;
		return redisTemplate.opsForZSet().remove(realKey, t);
	}

	/**
	 * 设置缓存值
	 * 
	 * (non-Javadoc)
	 * @see com.gws.utils.cache.CacheClient#set(java.lang.String, java.lang.Object, java.lang.Long, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> boolean set(String prefix, String key, T t, Long timeout) {
		if (key == null || prefix == null) {
			return false;
		}
		String realKey = prefix + key;
		redisTemplate.opsForValue().set(realKey, t, timeout, TimeUnit.SECONDS);
		return true;
	}

	/**
	 * 
	 * 设置缓存值
	 * 
	 * (non-Javadoc)
	 * @see com.opengroup.longmao.gwcommon.configuration.redis.cache.CacheClient#set(java.lang.String, java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> boolean set(String prefix, String key, T t) {
		if (key == null || prefix == null) {
			return false;
		}
		String realKey = prefix + key;
		redisTemplate.opsForValue().set(realKey, t);
		return true;
	}
	
	
	/**
	 * 
	 * 设置缓存值
	 * 
	 * (non-Javadoc)
	 * @see com.opengroup.longmao.gwcommon.configuration.redis.cache.CacheClient#set(java.lang.String, java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> boolean setBound(String prefix, String key, T t) {
		if (key == null || prefix == null) {
			return false;
		}
		String realKey = prefix + key;
		redisTemplate.boundValueOps(realKey).set(t);
		return true;
	}
	
	/**
	 * 
	 * 设置小键的值
	 * 
	 * (non-Javadoc)
	 * @see com.opengroup.longmao.gwcommon.configuration.redis.cache.CacheClient#hashSet(java.lang.String, java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public <T> boolean hashSet(String realKey, String hashKey, T t) {
		if (realKey == null || hashKey == null) {
			return false;
		}
		redisTemplate.opsForHash().put(realKey, hashKey, t);
		return true;
	}
	
	/**
	 * 
	 * 得到最外层的大键的值
	 * 
	 * (non-Javadoc)
	 * @see com.opengroup.longmao.gwcommon.configuration.redis.cache.CacheClient#hashGet(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public <T> T hashGet(String realKey) {
		if (realKey == null) {
			return null;
		}
		return (T) redisTemplate.opsForHash().entries(realKey);
	}
	
	/**
	 * 
	 * 根据大键和小键，得到小键的值
	 * 
	 * (non-Javadoc)
	 * @see com.opengroup.longmao.gwcommon.configuration.redis.cache.CacheClient#hashGet(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public <T> T hashGet(String realKey, String hashKey) {
		if (realKey == null || hashKey == null) {
			return null;
		}
		return (T) redisTemplate.opsForHash().get(realKey, hashKey);
	}
	
	/**
	 * 
	 * 【根据最外层大键删除所有的值】
	 * 
	 * (non-Javadoc)
	 * @see com.opengroup.longmao.gwcommon.configuration.redis.cache.CacheClient#hashDelete(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public <T> boolean hashDelete(String realKey) {
		if (realKey == null) {
			return false;
		}
		redisTemplate.delete(realKey);
		return true;
	}
	
	/**
	 * 
	 * @Title: hashDelete 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param realKey
	 * @param hashKey
	 * @return    设定文件 
	 * boolean    返回类型 
	 * @author Yangst
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public <T> boolean hashDelete(String realKey, String hashKey) {
		if (realKey == null || hashKey == null) {
			return false;
		}
		redisTemplate.opsForHash().delete(realKey, hashKey);
		return true;
	}
	
	/**
	 * 获取缓存值
	 * 
	 * (non-Javadoc)
	 * @see com.gws.utils.cache.CacheClient#get(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String prefix, String key) {
		if (key == null || prefix == null) {
			return null;
		}
		String realKey = prefix + key;
		return (T) redisTemplate.opsForValue().get(realKey);
	}

	/**
	 * 删除缓存
	 * 
	 * (non-Javadoc)
	 * @see com.gws.utils.cache.CacheClient#delete(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void delete(String prefix, String key) {
		if (key == null || prefix == null) {
			return;
		}
		String realKey = prefix + key;
		redisTemplate.delete(realKey);
	}

	/**
	 * 获取列表缓存
	 * 
	 * (non-Javadoc)
	 * @see com.gws.utils.cache.CacheClient#getList(java.lang.String, java.lang.String, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getList(String prefix, String key) {
		if (key == null || prefix == null) {
			return null;
		}
		String realKey = prefix + key;
		@SuppressWarnings("rawtypes")
		ListOperations listOps = redisTemplate.opsForList();
		return redisTemplate.opsForList().range(realKey, 0, listOps.size(realKey) - 1);
	}

	/**
	 * 【请在此输入描述文字】
	 * 
	 * (non-Javadoc)
	 * @see com.gws.utils.cache.CacheClient#setList(java.lang.String, java.lang.String, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> boolean setList(String prefix, String key, List<T> t) {
		if (key == null || prefix == null) {
			return false;
		}
		String realKey = prefix + key;
		redisTemplate.opsForList().leftPushAll(realKey, t);
		// redisTemplate.opsForList().rightPushAll(realKey, t);
		return false;
	}
	
	
	/****************************************/
	  
    /** 
     * 压栈 
     *  
     * @param key 
     * @param value 
     * @return 
     */  
	@SuppressWarnings("unchecked")
	public Long push(String key, String value) {
		if (key == null || value == null) {
			return null;
		}
		return redisTemplate.opsForList().leftPush(key, value);
	}
  
    /** 
     * 出栈 
     *  
     * @param key 
     * @return 
     */  
	@SuppressWarnings("unchecked")
	public String pop(String key) {
		if (key == null || key == null) {
			return null;
		}
		return (String) redisTemplate.opsForList().leftPop(key);
	}
  
    /** 
     * 入队 
     *  
     * @param key 
     * @param value 
     * @return 
     */  
	@SuppressWarnings("unchecked")
	public Long in(String key, String value) {
		if (key == null || value == null) {
			return null;
		}
		return redisTemplate.opsForList().rightPush(key, value);
	}
  
    /** 
     * 出队 
     *  
     * @param key 
     * @return 
     */  
	@SuppressWarnings("unchecked")
	public String out(String key) {
		if (key == null) {
			return null;
		}
		return (String) redisTemplate.opsForList().leftPop(key);
	}
  
    /** 
     * 栈/队列长 
     *  
     * @param key 
     * @return 
     */  
	@SuppressWarnings("unchecked")
	public Long length(String key) {
		if (key == null) {
			return null;
		}
		return redisTemplate.opsForList().size(key);
	}
  
    /** 
     * 范围检索 
     *  
     * @param key 
     * @param start 
     * @param end 
     * @return 
     */  
	@SuppressWarnings("unchecked")
	public List<String> range(String key, int start, int end) {
		if (key == null || start <= 0 || end <= 0) {
			return null;
		}
		return redisTemplate.opsForList().range(key, start, end);
	}
  
    /** 
     * 移除 
     *  
     * @param key 
     * @param i 
     * @param value 
     */  
	@SuppressWarnings("unchecked")
	public void remove(String key, long i, String value) {
		if (key != null && i >= 0 || value != null) {
			redisTemplate.opsForList().remove(key, i, value);
		}
	}
  
    /** 
     * 检索 
     *  
     * @param key 
     * @param index 
     * @return 
     */  
	@SuppressWarnings("unchecked")
	public String index(String key, long index) {
		if (key == null || index < 0) {
			return null;
		}
		return (String) redisTemplate.opsForList().index(key, index);
	}
  
    /** 
     * 置值 
     *  
     * @param key 
     * @param index 
     * @param value 
     */  
	@SuppressWarnings("unchecked")
	public void set(String key, long index, String value) {
		if (key != null && index >= 0 || value != null) {
			redisTemplate.opsForList().set(key, index, value);
		}
	}
  
    /** 
     * 裁剪 
     *  
     * @param key 
     * @param start 
     * @param end 
     */  
	@SuppressWarnings("unchecked")
	public void trim(String key, long start, int end) {
		if (key != null && start > 0 || end > 0) {
			redisTemplate.opsForList().trim(key, start, end);
		}
	}
}  
