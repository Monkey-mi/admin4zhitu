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
	 * 分页查询
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
	 * 更新
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
	 * 添加
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
	 * 批量删除
	 * @param idsStr
	 * @throws Exception
	 * @author zxx 2015-12-4 11:06:09
	 */
	public void batchDeleteNearLabel(String idsStr)throws Exception;
}
