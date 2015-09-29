package com.imzhitu.admin.interact.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.StarRecommendTopic;

public interface InteractStarRocommendTopicMapper {
	
	@DataSource("master")
	public void addStarRecommendTopic(StarRecommendTopic dto);
	
	@DataSource("slave")
	public List<StarRecommendTopic>  getStarRecommendTopic();
	
	@DataSource("slave")
	public List<Integer>  getStarRecommendTopicId();
	
	@DataSource("master")
	public void updateStarRecommendTopic(StarRecommendTopic dto);
	
	@DataSource("master")
	public void destoryStarRecommendTopic(Integer id);
}
