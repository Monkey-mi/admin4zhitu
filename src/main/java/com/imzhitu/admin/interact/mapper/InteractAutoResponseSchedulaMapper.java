package com.imzhitu.admin.interact.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.InteractAutoResponseSchedula;

public interface InteractAutoResponseSchedulaMapper {
	
	/**
	 * 添加
	 * @param dto
	 */
	public void addAutoResponseSchedula(InteractAutoResponseSchedula dto);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delInteractAutoResponseSchedula(Integer[] ids);
	
	/**
	 * 更新
	 * @param dto
	 */
	public void updateAutoResponseSchedula(InteractAutoResponseSchedula dto);
	
	/**
	 * 更新完成情况
	 * @param ids
	 */
	public void updateAutoResponseSchedulaComplete(Integer[] ids);
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<InteractAutoResponseSchedula> queryAutoResponseSchedula(InteractAutoResponseSchedula dto);
	
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryAutoResponseSchedulaCount(InteractAutoResponseSchedula dto);
	
	/**
	 * 查询未完成的计划
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<InteractAutoResponseSchedula> queryUnCompleteSchedula(InteractAutoResponseSchedula dto);
}
