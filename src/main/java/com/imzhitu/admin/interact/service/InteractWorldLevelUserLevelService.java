package com.imzhitu.admin.interact.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.InteractWorldLevelUserLevel;

public interface InteractWorldLevelUserLevelService extends BaseService{
	
	/**
	 * 分页查询
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryWorldLevelUserLevel(int maxId,int start,int limit,Map<String ,Object> jsonMap)throws Exception;
	
	/**
	 * 根据用户id查询织图等级与用户等级关联
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public InteractWorldLevelUserLevel queryWorldLevelUserLevelByUid(Integer uid)throws Exception;
	
	/**
	 * 增加织图等级与用户等级关联
	 * @param worldLevelId
	 * @param userLevelId
	 * @param operatorId
	 * @throws Exception
	 */
	public void addWorldLevelUserLevel(Integer worldLevelId,Integer userLevelId,Integer operatorId)throws Exception;
	
	/**
	 * 删除织图等级与用户等级的关联
	 * @param idsStr
	 * @throws Exception
	 */
	public void delWorldLevelUserLevel(String idsStr)throws Exception;
}
