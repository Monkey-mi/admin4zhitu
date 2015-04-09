package com.imzhitu.admin.interact.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.InteractWorldLevelUserLevel;

public interface InteractWorldLevelUserLevelMapper {
	/**
	 * 查询世界等级用户等级
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<InteractWorldLevelUserLevel> queryWorldLevelUserLevel(InteractWorldLevelUserLevel dto);
	
	/**
	 * 查询世界等级用户等级总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryWorldLevelUserLevelTotal(InteractWorldLevelUserLevel dto);
	
	/**
	 * 根据用户id 查询世界等级用户等级
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public InteractWorldLevelUserLevel queryWorldLevelUserLevelByUid(InteractWorldLevelUserLevel dto);
	
	/**
	 * 增加
	 * @param dto
	 */
	public void addWorldLevelUserLevel(InteractWorldLevelUserLevel  dto);
	
	/**
	 * 删除
	 * @param id
	 */
	public void delWorldLevelUserLevelById(Integer id);
}
