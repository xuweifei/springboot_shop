package com.opengroup.longmao.gwcommon.configuration.elasticsearch.service;

import java.util.List;

import org.elasticsearch.search.sort.SortOrder;

import io.searchbox.core.Index;

public interface ESService<T> {
	/**
	 * 单个新增
	 * @param t
	 * @return
	 */
	public Index saveEntity(T t);

	/**
	 * 批量新增
	 * @param entityList
	 */
   public void saveEntity(List<T> entityList);
    /**
     * 多列查询
     * @param searchContent
     * @param index
     * @return
     */
    public Object searchmore(T t,String searchContent, String... index);
    /**
	 * 分页查询
	 * @param t
	 * @param pageNumber:第几页，从一开始
	 * @param pageSize:每页数量
	 * @param sort:排序的字段
	 * @param order:SortOrder.DESC  SortOrder.ASC
	 * @param searchContent：查询数据
	 * @param index：查询的列
	 * @return
	 */
	public Object searchPage(T t,int pageNumber,int pageSize,String sort,SortOrder order,String searchContent, String... index);
}
