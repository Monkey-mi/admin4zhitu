package com.imzhitu.admin.ztworld.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hts.web.common.service.BaseService;

/**
 * 
 * @author zxx
 *
 */

public interface ZTWorldTypeWorldSchedulaService extends BaseService{
	/**
	 * 增加分类织图计划
	 * @param type_world_id
	 * @param schedula
	 * @param complete
	 * @throws Exception
	 */
	public void addTypeWorldSchedula(Integer type_world_id,Date schedula,Integer operatorId,Integer complete)throws Exception;
	
	/**
	 * 根据world ids删除分类织图计划
	 * @param idStr
	 * @throws Exception
	 */
	public void delTypeWorldSchedulaByIds(String idStr)throws Exception;
	
	/**
	 * 分页查询织图计划
	 * @param maxSchedula
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryTypeWorldSchedula(Date maxSchedula,Integer wid,Integer timeType,Date beginTime,Date endTime,int page, int rows, Map<String, Object> jsonMap)throws Exception;
	
	/**
	 * 更新分类织图计划
	 * @param ids
	 * @param complete
	 * @throws Exception
	 */
	public void updateCompleteByIds(Integer[] ids,Integer complete)throws Exception;
	
	/**
	 * 查询分类织图计划
	 * @param begin
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getWorldIdBySchedula(Date begin,Date end)throws Exception;
	
	/**
	 * 重新排序
	 * @param wids
	 * @param schedula
	 * @param operatorId
	 * @throws Exception
	 */
	public void reSort(String[] wids,Date schedula,Integer operatorId)throws Exception;
}
