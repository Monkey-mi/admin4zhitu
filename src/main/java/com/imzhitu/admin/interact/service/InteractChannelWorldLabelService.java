package com.imzhitu.admin.interact.service;

import java.util.List;
import java.util.Map;

import com.imzhitu.admin.common.pojo.InteractChannelWorldLabel;

public interface InteractChannelWorldLabelService {
	/**
	 * 增加
	 * @param channelId
	 * @param worldId
	 * @param label_ids
	 * @param operator
	 * @throws Exception
	 */
	public void insertChannelWorldLabel(Integer channelId,Integer worldId,String label_ids,Integer operator)throws Exception;
	
	/**
	 * 查询
	 * @param id
	 * @param worldId
	 * @param channelId
	 * @return
	 * @throws Exception
	 */
	public List<InteractChannelWorldLabel> queryChannelWorldLabel(Integer id,Integer worldId,Integer channelId)throws Exception;
	
	/**
	 * 查询总数
	 * @param id
	 * @param worldId
	 * @param channelId
	 * @return
	 * @throws Exception
	 */
	public long queryChannelWorldLabelTotalCount(Integer id,Integer worldId,Integer channelId)throws Exception;
	
	/**
	 * 查询该织图对应的标签，若是没有，则查询该频道绑定的标签
	 * @param worldId
	 * @param channelId
	 * @throws Exception
	 */
	public void queryChannelWorldLabel(Integer worldId,Integer channelId,Map<String,Object>jsonMap)throws Exception;
}
