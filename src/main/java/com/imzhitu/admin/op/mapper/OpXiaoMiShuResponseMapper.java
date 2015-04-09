package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
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
	 * 插入小秘书模块
	 * @param vo
	 */
	public void insertResponseModule(OpXiaoMiShuResponse vo);
	
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
	 * 删除小秘书模块
	 * @param vo
	 */
	public void deleteResponseModule(OpXiaoMiShuResponse vo);
	
	/**
	 * 更新小秘书回复字典
	 * @param vo
	 */
	public void updateResponse(OpXiaoMiShuResponse vo);
	
	/**
	 * 更新小秘书key
	 * @param vo
	 */
	public void updateResponseKey(OpXiaoMiShuResponse vo);
	
	/**
	 * 更新小秘书模块
	 * @param vo
	 */
	public void updateResponseModule(OpXiaoMiShuResponse vo);
	
	/**
	 * 分页查询
	 * @param vo
	 * @return
	 */
	@DataSource("slave")
	public List<OpXiaoMiShuResponse> queryResponseAndKey(OpXiaoMiShuResponse vo);
	
	
	/**
	 * 分页查询总数
	 * @param vo
	 * @return
	 */
	@DataSource("slave")
	public long queryResponseAndKeyTotalCount(OpXiaoMiShuResponse vo);
	
	/**
	 * 查询所有的小秘书模块
	 * @param vo
	 * @return
	 */
	@DataSource("slave")
	public List<OpXiaoMiShuResponse>queryResponseModule(OpXiaoMiShuResponse vo);
	
	/**
	 * 分页查询模块
	 * @param vo
	 * @return
	 */
	@DataSource("slave")
	public List<OpXiaoMiShuResponse> queryResponseModuleForTable(OpXiaoMiShuResponse vo);
	
	/**
	 * 分页查询模块总数
	 * @param vo
	 * @return
	 */
	@DataSource("slave")
	public long queryResponseModuleTotalCount(OpXiaoMiShuResponse vo);
	
	/**
	 * 分页查询回复内容 
	 * @param vo
	 * @return
	 */
	@DataSource("slave")
	public List<OpXiaoMiShuResponse> queryResponseContentForTable(OpXiaoMiShuResponse vo);
	
	/**
	 * 分页查询回复内容总数
	 * @param vo
	 * @return
	 */
	@DataSource("slave")
	public long queryResponseContentTotalCount(OpXiaoMiShuResponse vo);
}
