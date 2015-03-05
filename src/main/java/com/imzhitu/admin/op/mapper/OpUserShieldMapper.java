package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.pojo.OpUserShieldDto;

public interface OpUserShieldMapper {
	/**
	 * 增加
	 * @param dto
	 */
	public void addUserShield(OpUserShieldDto dto);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delUserShield(Integer[] ids);
	
	/**
	 * 更新
	 * @param dto
	 */
	public void updateUserShield(OpUserShieldDto dto);
	
	/**
	 * 查询总数
	 */
	public long queryUserShieldCount(OpUserShieldDto dto);
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	public List<OpUserShieldDto> queryUserShieldForList(OpUserShieldDto dto);
}
