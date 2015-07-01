package com.imzhitu.admin.op.service;

import java.util.Map;

public interface OpZombieDegreeUserLevelService {
	/**
	 * 增加
	 * @param zombieDegreeId
	 * @param userLevelId
	 * @throws Exception
	 */
	public void insertZombieDegreeUserLevel(Integer zombieDegreeId,Integer userLevelId)throws Exception;
	
	/**
	 * 批量删除
	 * @param idsStr
	 * @throws Exception
	 */
	public void batchDeleteZombieDegreeUserLevel(String idsStr)throws Exception;
	
	/**
	 * 分页查询
	 * @param id
	 * @param zombieDegreeId
	 * @param userLevelId
	 * @param maxId
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryZombieDegreeUserLevel(Integer id,Integer zombieDegreeId,Integer userLevelId,Integer maxId,int page,int rows ,Map<String,Object>jsonMap)throws Exception;
	
	/**
	 * 
	 * @param id
	 * @param zombieDegreeId
	 * @param userLevelId
	 * @param maxId
	 * @return
	 * @throws Exception
	 */
	public long queryZombieDegreeUserLevelTotalCount(Integer id,Integer zombieDegreeId,Integer userLevelId,Integer maxId)throws Exception;
}
