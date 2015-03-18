package com.imzhitu.admin.op.service;

import java.util.List;
import java.util.Map;

import com.imzhitu.admin.common.pojo.OpXiaoMiShuResponse;

public interface OpXiaoMiShuResponseService {
	/**
	 * 插入小秘书回复字典
	 * @param id can be null. 
	 * 
	 */
	public void insertResponse(Integer responseId,String content,Integer moduleId,Integer operatorId)throws Exception;
	
	/**
	 * 插入小秘书回复key字典
	 * @param vo
	 */
	public void insertResponseKey(Integer keyId,Integer moduleId,String key,Integer keyValid,Integer responseId,Integer operatorId)throws Exception;
	
	/**
	 * 插入小秘书模块
	 * @param vo
	 */
	public void insertResponseModule(Integer moduleId,String moduleName,Integer moduleValid,Integer operatorId)throws Exception;
	
	/**
	 * 删除小秘书回复字典
	 * @param vo
	 */
	public void deleteResponse(Integer responseId)throws Exception;
	
	/**
	 * 删除小秘书回复key字典
	 * @param vo
	 */
	public void deleteResponseKey(Integer keyId)throws Exception;
	
	/**
	 * 删除小秘书模块
	 * @param vo
	 */
	public void deleteResponseModule(Integer moduleId,Integer moduleValid)throws Exception;
	
	/**
	 * 更新小秘书回复字典
	 * @param vo
	 */
	public void updateResponse(Integer responseId,String content,Integer operatorId)throws Exception;
	
	/**
	 * 更新小秘书key
	 * @param vo
	 */
	public void updateResponseKey(Integer keyId,String key,Integer keyValid,Integer operatorId)throws Exception;
	
	/**
	 * 更新小秘书模块
	 * @param vo
	 */
	public void updateResponseModule(Integer moduleId,Integer moduleValid,Integer operatorId)throws Exception;
	
	/**
	 * 查询
	 * @param vo
	 * @return
	 */
	public void queryResponseAndKey(Integer maxId,int page,int rows,
			Integer keyId,Integer moduleId,String key,Integer keyValid,Integer responseId,Map<String, Object> jsonMap)throws Exception;
	
	/**
	 * 查询所有的小秘书模块
	 * @param vo
	 * @return
	 */
	public List<OpXiaoMiShuResponse>queryResponseModule(Integer moduleId,String moduleName,Integer moduleValid)throws Exception;
	
	/**
	 * 分页查询模块
	 * @param vo
	 * @return
	 */
	public void queryResponseModuleForTable(Integer maxId,int page,int rows,
			Integer moduleId,Integer moduleValid,Map<String, Object> jsonMap)throws Exception;
	
	
	/**
	 * 分页查询回复内容 
	 * @param vo
	 * @return
	 */
	public void queryResponseContentForTable(Integer maxId,int page,int rows,
			Integer moduleId,Map<String, Object> jsonMap)throws Exception;
	
	/**
	 * 批量增加key
	 * @param keyStr
	 * @param responseId
	 * @throws Exception
	 */
	public void batchAddResponseKey(String keyStr,Integer moduleId,Integer responseId,Integer operatorId)throws Exception;
	
	/**
	 * 批量删除key
	 * @param keyIdStr
	 * @throws Exception
	 */
	public void batchDelResponseKey(String keyIdStr)throws Exception;
	
}
