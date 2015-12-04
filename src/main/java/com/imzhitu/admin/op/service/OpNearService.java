package com.imzhitu.admin.op.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;

/**
 * 附近模块 业务层接口
 * @author zxx 2015-12-4 11:06:09
 *
 */
public interface OpNearService extends BaseService{
	/**
	 * 分页查询附近标签
	 * @param id
	 * @param cityId
	 * @param maxSerial
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 * @author zxx 2015-12-4 11:06:09
	 */
	public void queryNearLabel(Integer id,Integer cityId,int maxSerial,int start,int limit,Map<String,Object>jsonMap)throws Exception;
	
	/**
	 * 更新 附近标签
	 * @param id
	 * @param cityId
	 * @param labelName
	 * @param longitude
	 * @param latitude
	 * @param description
	 * @param bannerUrl
	 * @param serial
	 * @throws Exception
	 * @author zxx 2015-12-4 11:06:09
	 */
	public void updateNearLabel(Integer id,Integer cityId,String labelName,Double longitude,Double latitude,String description,String bannerUrl,Integer serial)throws Exception;
	
	/**
	 * 添加附近标签
	 * @param id
	 * @param cityId
	 * @param labelName
	 * @param longitude
	 * @param latitude
	 * @param description
	 * @param bannerUrl
	 * @param serial
	 * @throws Exception
	 * @author zxx 2015-12-4 11:06:09
	 */
	public void insertNearLabel(Integer id,Integer cityId,String labelName,Double longitude,Double latitude,String description,String bannerUrl,Integer serial)throws Exception;
	
	/**
	 * 批量删除附近标签
	 * @param idsStr
	 * @throws Exception
	 * @author zxx 2015-12-4 11:06:09
	 */
	public void batchDeleteNearLabel(String idsStr)throws Exception;
	
	/**
	 * 插入附近城市组
	 * @throws Exception
	 * @author zxx 2015-12-4 16:56:02
	 */
	public void insertNearCityGroup(String description)throws Exception;
	
	/**
	 * 批量删除附近城市组
	 * @param idsStr
	 * @throws Exception
	 * @author zxx 2015-12-4 16:56:02
	 */
	public void batchDeleteNearCityGroup(String idsStr)throws Exception;
	
	/**
	 * 查询附近城市组
	 * @param id
	 * @param maxSerial
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 * @author zxx 2015-12-4 16:56:02
	 */
	public void queryNearCityGroup(Integer id,int maxSerial,int start,int limit,Map<String,Object>jsonMap)throws Exception;
	
	/**
	 * 插入附近推荐城市
	 * @param cityId
	 * @param cityGroupId
	 * @throws Exception
	 * @author zxx 2015-12-4 17:33:05
	 */
	public void insertNearRecommendCity(Integer cityId,Integer cityGroupId)throws Exception;
	
	/**
	 * 批量删除附近推荐城市
	 * @param idsStr
	 * @throws Exception
	 * @author zxx 2015-12-4 17:33:05
	 */
	public void batchDeleteNearRecommendCity(String idsStr)throws Exception;
	
	/**
	 * 查询附近默认城市
	 * @param id
	 * @param cityId
	 * @param cityGroupId
	 * @param maxSerial
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 * @author zxx 2015-12-4 17:33:05
	 */
	public void queryNearRecommendCity(Integer id,Integer cityId,Integer cityGroupId,int maxSerial,int start,int limit,Map<String,Object>jsonMap)throws Exception;
}