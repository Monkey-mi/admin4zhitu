package com.imzhitu.admin.op.service;

import java.util.List;

import com.imzhitu.admin.common.pojo.OpXiaoMiShuResponse;

public interface OpXiaoMiShuResponseService {
	/**
	 * 插入小秘书回复字典
	 * @param id can be null. 
	 * 
	 */
	public void insertResponse(Integer id,String content)throws Exception;
	
	/**
	 * 插入小秘书回复key字典
	 * @param vo
	 */
	public void insertResponseKey(Integer id,String module,String key,Integer keyLength,Integer responseId)throws Exception;
	
	/**
	 * 删除小秘书回复字典
	 * @param vo
	 */
	public void deleteResponse(Integer id)throws Exception;
	
	/**
	 * 删除小秘书回复key字典
	 * @param vo
	 */
	public void deleteResponseKey(Integer id)throws Exception;
	
	/**
	 * 更新小秘书回复字典
	 * @param vo
	 */
	public void updateResponse(Integer id,String content)throws Exception;
	
	/**
	 * 查询
	 * @param vo
	 * @return
	 */
	public List<OpXiaoMiShuResponse> queryResponseAndKey(Integer keyId,String module,String key,Integer keyLength,Integer responseId)throws Exception;
}
