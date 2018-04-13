package com.opengroup.longmao.gwcommon.configuration.elasticsearch.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

import com.opengroup.longmao.gwcommon.configuration.elasticsearch.service.ESService;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.tools.idutil.StringUtil;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

@Service
public class ESServiceImpl<T> implements ESService<T>{

	@Autowired
    private JestClient jestClient;
	
	/**
	 * 单个新增
	 */
	@Override
	public Index saveEntity(T t) {
		try {
			String string = t.getClass().getName();
			String[] str = string.split("\\.");
			String ss = str[str.length-1].toLowerCase()+CommConstant.ES_INDEX_NAME;
			
			Index index = new Index.Builder(t).index(ss).type(CommConstant.ES_TYPE).build();
			try {
			    jestClient.execute(index);
			    GwsLogger.info("ES 单个插入完成");
			    return  index;
			} catch (IOException e) {
			    e.printStackTrace();
			    GwsLogger.error("ES 单个插入失败，e={}",e.getMessage());
			    return null;
			}
		} catch (Exception e) {
			GwsLogger.error("ES 单个插入失败，e={}",e.getMessage());
		    return null;
		}
	}

	/**
	 * 批量新增
	 */
	@Override
	public void saveEntity(List<T> entityList) {
		try {
			Bulk.Builder bulk = new Bulk.Builder();
			for(T entity : entityList) {
				String string = entity.getClass().getName();
				String[] str = string.split("\\.");
				String ss = str[str.length-1].toLowerCase()+CommConstant.ES_INDEX_NAME;
				
			    Index index = new Index.Builder(entity).index(ss).type(CommConstant.ES_TYPE).build();
			    bulk.addAction(index);
			}
			try {
			    jestClient.execute(bulk.build());
			    GwsLogger.info("ES 批量插入完成,条数："+entityList.size());
			} catch (IOException e) {
			    e.printStackTrace();
			    GwsLogger.error("ES 批量插入失败，e={}",e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		    GwsLogger.error("ES 批量插入失败，e={}",e.getMessage());
		}
		
	}

	/**
	 * 多列查询
	 */
	@Override
	public Object searchmore(T t,String searchContent, String... index) {
		long bigintime = System.currentTimeMillis();
		String string = t.getClass().getName();
		String[] str = string.split("\\.");
		String ss = str[str.length-1].toLowerCase()+CommConstant.ES_INDEX_NAME;
		
		QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(searchContent, index);
		
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);

        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(ss).addType(CommConstant.ES_TYPE).build();
        try {
            JestResult result = jestClient.execute(search);
            long endtime = System.currentTimeMillis();
            GwsLogger.info("ES多列查询成功,T={},searchContent={},耗时={}",t,searchContent,endtime-bigintime);
            return result.getSourceAsObjectList(t.getClass());
        } catch (IOException e) {
        	long endtime = System.currentTimeMillis();
        	GwsLogger.error("ES多列查询失败,T={},searchContent={},耗时={},e={}",t,searchContent,endtime-bigintime,e.getMessage());
            e.printStackTrace();
        }
        return null;
	}
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
	public Object searchPage(T t,int pageNumber,int pageSize,String sort,SortOrder order,String searchContent, String... index){
		long bigintime = System.currentTimeMillis();
		String string = t.getClass().getName();
		String[] str = string.split("\\.");
		String ss = str[str.length-1].toLowerCase()+CommConstant.ES_INDEX_NAME;
		
		QueryBuilder queryBuilders = QueryBuilders.multiMatchQuery(searchContent, index);
		
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilders);
        
        if(pageNumber>=1 && pageSize>0){
        	searchSourceBuilder.from((pageNumber - 1) * pageSize);//设置起始页
            searchSourceBuilder.size(pageSize);//设置页大小
        }
        if(!StringUtil.isBlank(sort) && order != null){
        	searchSourceBuilder.sort(sort, order);//排序
        }
        
        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(ss).addType(CommConstant.ES_TYPE).build();
        try {
            JestResult result = jestClient.execute(search);
            long endtime = System.currentTimeMillis();
            GwsLogger.info("ES多列分页排序查询成功,T={},searchContent={},sort={},耗时={}",t,searchContent,sort,endtime-bigintime);
            return result.getSourceAsObjectList(t.getClass());
        } catch (IOException e) {
        	long endtime = System.currentTimeMillis();
        	GwsLogger.error("ES多列分页排序查询失败,T={},searchContent={},sort={},耗时={},e={}",t,searchContent,sort,endtime-bigintime,e.getMessage());
            e.printStackTrace();
        }
        return null;
		
	}

}
