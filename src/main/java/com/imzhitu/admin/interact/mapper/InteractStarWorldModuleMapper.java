package com.imzhitu.admin.interact.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.StarModule;

public interface InteractStarWorldModuleMapper {
	
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
	public void addStarWorldModule(StarModule dto);
	

	@DataSource("slave")
	public Integer  getStarWorldModuleCount(@Param("topicId")Integer topicId);
	
	
	@DataSource("slave")
	public List<StarModule>  getStarWorldModule(@Param("start")Integer start,@Param("limites")Integer rows,@Param("maxId")Integer maxId,@Param("topicId")Integer topicId);
	
	@DataSource("master")
	public void updateStarWorldModule(StarModule dto);
	
	@DataSource("master")
	public void destroyStarWorldModule(Integer id);
	
	@DataSource("master")
	public void destoryByTopicId(Integer topicId);
	
	@DataSource("master")
	public void reOrderIndex(@Param("id")Integer id,@Param("orderIndex")Integer orderIndex);
}
