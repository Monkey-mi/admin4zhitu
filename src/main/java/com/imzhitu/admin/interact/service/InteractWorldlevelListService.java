package com.imzhitu.admin.interact.service;

import java.util.Date;
import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.InteractWorldLevelListDto;

public interface InteractWorldlevelListService extends BaseService{
	/**
	 * 查询等级织图列表
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryWorldlevelList(int maxId,Integer wid,Integer timeType,Date beginTime,Date endTime,int start,int limit,Map<String ,Object> jsonMap)throws Exception;
	
	/**
	 * 增加等级织图
	 * @param world_id
	 * @param world_level_id
	 * @param validity
	 * @param comment_ids
	 * @param label_ids
	 * @throws Exception
	 */
	public void addWorldlevelList(Integer world_id,Integer world_level_id,Integer validity,String comment_ids,String label_ids,Integer operatorId)throws Exception;
	
	
	/**
	 * 根据织图等级ids删除等级织图
	 * @param widsStr
	 * @throws Exception
	 */
	public void delWorldlevelListByWIds(String widsStr)throws Exception;
	
	/**
	 * 跟新等级织图的等级和有效性
	 * @param world_id
	 * @param world_level_id
	 * @param validity
	 * @throws Exception
	 */
	public void updateWorldlevelListValidity(Integer world_id,Integer world_level_id,Integer validity,Integer operatorId)throws Exception;
	
	/**
	 * 跟新等级织图所有的信息
	 * @param worldId
	 * @param world_level_id
	 * @param validity
	 * @param label_ids
	 * @param comment_ids
	 * @throws Exception
	 */
	public void updateWorldLevelList(Integer worldId,Integer world_level_id,Integer validity,String label_ids,String comment_ids,Integer operatorId)throws Exception;
	
	/**
	 * 根据织图id查询等级织图
	 * @param wid
	 * @return
	 * @throws Exception
	 */
	public InteractWorldLevelListDto queryWorldLevelListByWid(Integer wid)throws Exception;
	
	/**
	 * 根据worldId，查询添加了多少互动
	 * @param wid
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryWorldUNInteractCount(Integer wid,Map<String,Object> jsonMap)throws Exception;
	
	/**
	 * 查询wid是否被添加到worldlevelList中
	 */
	public boolean chechWorldLevelListIsExistByWId(Integer wid)throws Exception;
}
