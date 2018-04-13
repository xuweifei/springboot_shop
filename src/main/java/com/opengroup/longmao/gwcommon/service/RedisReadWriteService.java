/**
 * @Title: RedisReadWriteService.java 
 * @Package org.spring.cloud.live.service 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.service;

import java.util.List;

/**
 * @ClassName: RedisReadWriteService
 * @Description: Redis读写Service
 * @author Mr.Zhu
 */
public interface RedisReadWriteService {
	
	/**
	 * @Title: zSetSize 
	 * @Description: 获取sortedset数量 
	 * @param prefix
	 * @param key
	 * @return Integer
	 */
	<T> Integer zSetSize(String prefix, String key);
	
	/**
	 * @Title: hashSize 
	 * @Description: 获取hashset数量 
	 * @param realKey
	 * @return Long
	 */
	<T> Long hashSize(String realKey);

	/**
	 * @Title: zSet
	 * @Description: 设置sortedset值
	 * @param prefix
	 * @param key
	 * @param t
	 * @param score
	 * @return boolean
	 */
	<T> boolean zSet(String prefix, String key, T t, double score);

	/**
	 * @Title: zGetDesc
	 * @Description: 读取sortedset值
	 * @param prefix
	 * @param key
	 * @param start
	 * @param end
	 * @return T
	 */
	<T> T zGetDesc(String prefix, String key, Long start, Long end);
	
	/**
     * @Title: zGetAsc 
     * @Description: 读取sortedset值 
     * @param prefix
     * @param key
     * @param start
     * @param end
     * @return T
     */
	<T> T zGetAsc(String prefix, String key, Long start, Long end);
	
	/**
	 * @Title: zGetScore 
	 * @Description: 读取sortedset值 
	 * @param prefix
	 * @param key
	 * @param t
	 * @return T
	 */
	<T> T zGetScore(String prefix, String key, T t);

	/**
	 * @Title: zRemove
	 * @Description: 删除sortedset值
	 * @param prefix
	 * @param key
	 * @param t
	 * @return Long
	 */
	<T> Long zRemove(String prefix, String key, T t);

	/**
	 * @Title: get
	 * @Description: 读取Redis值
	 * @param prefix 后缀
	 * @param key 健
	 * @return T
	 */
	<T> T get(String prefix, String key);
	
	/**
	 * @Title: set 
	 * @Description: 写入Redis值 
	 * @param prefix 后缀
	 * @param key 健
	 * @param t 写入值
	 * @return boolean
	 */
	<T> boolean set(String prefix, String key, T t);

	/**
	 * @Title: set
	 * @Description: 写入Redis值
	 * @param prefix 后缀
	 * @param key 健
	 * @param t 写入值
	 * @param timeout 值失效时间
	 * @return boolean
	 */
	<T> boolean set(String prefix, String key, T t, Long timeout);

	/**
	 * @Title: delete
	 * @Description: 删除Redis值
	 * @param prefix  后缀
	 * @param key  健
	 * @return boolean
	 */
	<T> boolean delete(String prefix, String key);

	/**
	 * @Title: getList
	 * @Description: 读取Redis值-LIST
	 * @param prefix 后缀
	 * @param key  健
	 * @return List<T>
	 */
	<T> List<T> getList(String prefix, String key);

	/**
	 * @Title: setList
	 * @Description: 写入Redis值-LIST
	 * @param prefix 后缀
	 * @param key 健
	 * @param t LIST
	 * @return boolean
	 */
	<T> boolean setList(String prefix, String key, List<T> t);
	
	/**
	 * @Title: hashGet 
	 * @Description: 读取Redis值-HASH 
	 * @param realKey 外层大健
	 * @return T
	 */
	<T> T hashGet(String realKey);

	/**
	 * @Title: hashGet
	 * @Description: 读取Redis值-HASH
	 * @param realKey 外层大健
	 * @param hashKey 内层小健
	 * @return T
	 */
	<T> T hashGet(String realKey, String hashKey);

	/**
	 * @Title: hashSet
	 * @Description: 写入Redis值-HASH
	 * @param realKey 外层大健
	 * @param hashKey 内层小健
	 * @param t
	 * @return boolean
	 */
	<T> boolean hashSet(String realKey, String hashKey, T t);

	/**
	 * @Title: hashDelete
	 * @Description: 删除Redis值-HASH
	 * @param realKey 外层大健
	 * @param hashKey 内层小健
	 * @return boolean
	 */
	<T> boolean hashDelete(String realKey, String hashKey);
}
