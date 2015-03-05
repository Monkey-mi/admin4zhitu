package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.pojo.OpXiaoMiShuResponse;

public interface OpXiaoMiShuResponseMapper {
	/**
	 * 插入小秘书回复字典
	 * @param vo
	 */
	public void insertResponse(OpXiaoMiShuResponse vo);
	
	/**
	 * 插入小秘书回复key字典
	 * @param vo
	 */
	public void insertResponseKey(OpXiaoMiShuResponse vo);
	
	/**
	 * 删除小秘书回复字典
	 * @param vo
	 */
	public void deleteResponse(OpXiaoMiShuResponse vo);
	
	/**
	 * 删除小秘书回复key字典
	 * @param vo
	 */
	public void deleteResponseKey(OpXiaoMiShuResponse vo);
	
	/**
	 * 更新小秘书回复字典
	 * @param vo
	 */
	public void updateResponse(OpXiaoMiShuResponse vo);
	
	/**
	 * 查询
	 * @param vo
	 * @return
	 */
	public List<OpXiaoMiShuResponse> queryResponseAndKey(OpXiaoMiShuResponse vo);
}
