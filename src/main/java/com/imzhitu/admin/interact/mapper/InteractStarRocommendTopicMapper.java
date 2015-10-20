package com.imzhitu.admin.interact.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.StarRecommendTopic;

public interface InteractStarRocommendTopicMapper {
	
	@DataSource("master")
	public void addStarRecommendTopic(StarRecommendTopic dto);
	
	@DataSource("slave")
	public List<StarRecommendTopic>  getStarRecommendTopic(@Param("isWorld") Integer isWorld);
	
	@DataSource("slave")
	public List<Integer>  getStarRecommendTopicId();
	
	@DataSource("master")
	public void updateStarRecommendTopic(StarRecommendTopic dto);
	
	@DataSource("master")
	public void destoryStarRecommendTopic(Integer id);
}
