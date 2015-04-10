package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpUserShieldDto;

public interface OpUserShieldMapper {
	/**
	 * 增加
	 * @param dto
	 */
	@DataSource("master")
	public void addUserShield(OpUserShieldDto dto);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@DataSource("master")
	public void delUserShield(Integer[] ids);
	
	/**
	 * 更新
	 * @param dto
	 */
	@DataSource("master")
	public void updateUserShield(OpUserShieldDto dto);
	
	/**
	 * 查询总数
	 */
	@DataSource("slave")
	public long queryUserShieldCount(OpUserShieldDto dto);
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<OpUserShieldDto> queryUserShieldForList(OpUserShieldDto dto);
}
