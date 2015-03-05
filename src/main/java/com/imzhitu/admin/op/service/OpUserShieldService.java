package com.imzhitu.admin.op.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;

public interface OpUserShieldService extends BaseService{
	/**
	 * 增加
	 * @param userId
	 * @param operatorId
	 * @param valid
	 */
	public void addUserShield(Integer userId,Integer operatorId,Integer valid)throws Exception;
	
	/**
	 * 批量删除
	 * @param idsStr
	 * @throws Exception
	 */
	public void delUserShield(String idsStr)throws Exception;
	
	/**
	 * 修改
	 * @param id
	 * @param userId
	 * @param valid
	 * @param operatorId
	 * @throws Exception
	 */
	public void updateUserShield(Integer id,Integer userId,Integer valid,Integer operatorId)throws Exception;
	
	
	/**
	 * 分页查询
	 * @param maxId
	 * @param page
	 * @param rows
	 * @param id
	 * @param userId
	 * @param valid
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryUserShieldForList(Integer maxId, int page, int rows, 
			Integer id, Integer userId, Integer valid, Map<String, Object> jsonMap)throws Exception;
}
