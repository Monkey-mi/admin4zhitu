package com.imzhitu.admin.interact.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.StarModule;

public interface InteractStarModuleMapper {
	
	/**
	 * 
	 * @param title1
	 * @param title2
	 * @param userId
	 * @param pics
	 * @param Intro 
	 *	2015年9月21日
	 *	mishengliang
	 */
	@DataSource("master")
	public void addStarModule(StarModule dto);
	
	@DataSource("slave")
	public List<StarModule>  getStarModule(@Param("topicId")Integer topicId);
	
	@DataSource("master")
	public void updateStarModule(StarModule dto);
	
	@DataSource("master")
	public void destory(Integer id);
}
