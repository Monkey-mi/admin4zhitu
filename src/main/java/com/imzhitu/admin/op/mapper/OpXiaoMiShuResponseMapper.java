package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpXiaoMiShuResponse;

public interface OpXiaoMiShuResponseMapper {
	/**
	 * 插入小秘书回复字典
	 * @param vo
	 */
	@DataSource("master")
	public void insertResponse(OpXiaoMiShuResponse vo);
	
	/**
	 * 插入小秘书回复key字典
	 * @param vo
	 */
	@DataSource("master")
	public void insertResponseKey(OpXiaoMiShuResponse vo);
	
	/**
	 * 插入小秘书模块
	 * @param vo
	 */
	@DataSource("master")
	public void insertResponseModule(OpXiaoMiShuResponse vo);
	
	/**
	 * 删除小秘书回复字典
	 * @param vo
	 */
	@DataSource("master")
	public void deleteResponse(OpXiaoMiShuResponse vo);
	
	/**
	 * 删除小秘书回复key字典
	 * @param vo
	 */
	@DataSource("master")
	public void deleteResponseKey(OpXiaoMiShuResponse vo);
	
	/**
	 * 删除小秘书模块
	 * @param vo
	 */
	@DataSource("master")
	public void deleteResponseModule(OpXiaoMiShuResponse vo);
	
	/**
	 * 更新小秘书回复字典
	 * @param vo
	 */
	@DataSource("master")
	public void updateResponse(OpXiaoMiShuResponse vo);
	
	/**
	 * 更新小秘书key
	 * @param vo
	 */
	@DataSource("master")
	public void updateResponseKey(OpXiaoMiShuResponse vo);
	
	/**
	 * 更新小秘书模块
	 * @param vo
	 */
	@DataSource("master")
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
