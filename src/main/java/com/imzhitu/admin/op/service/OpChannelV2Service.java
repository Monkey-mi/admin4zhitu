package com.imzhitu.admin.op.service;

import java.util.Map;

import net.sf.json.JSONArray;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.OpChannelV2Dto;

public interface OpChannelV2Service extends BaseService{
	/**
	 * 增加
	 * @param dto
	 */
	public void insertOpChannel(Integer ownerId,String channelName,String channelTitle,String subtitle,String channelDesc,
			String channelIcon,String subIcon,Integer channelTypeId,String channelLabelNames,String channelLabelIds,Integer worldCount,
			Integer worldPictureCount,Integer memberCount,Integer  superbCount,Integer childCountBase,Integer superb,Integer valid,
			Integer serial,Integer danmu,Integer moodFlag,Integer worldFlag)throws Exception;
	
	/**
	 * 删除
	 * @param dto
	 */
	public void deleteOpChannel(Integer channelId,Integer ownerId)throws Exception;
	
	/**
	 * 修改
	 * @param dto
	 */
	public void updateOpChannel(Integer channelId,Integer ownerId,String channelName,String channelTitle,String subtitle,String channelDesc,
			String channelIcon,String subIcon,Integer channelTypeId,String channelLabelNames,String channelLabelIds,Integer worldCount,
			Integer worldPictureCount,Integer memberCount,Integer  superbCount,Integer childCountBase,Integer superb,Integer valid,
			Integer serial,Integer danmu,Integer moodFlag,Integer worldFlag)throws Exception;
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	public void queryOpChannel(Integer channelId,Integer ownerId,Integer superb,Integer valid,
			Integer serial,Integer danmu,Integer moodFlag,Integer worldFlag,int start,int rows,Integer maxId,Map<String,Object>jsonMap)throws Exception;
	
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	public long queryOpChannelTotalCount(Integer channelId,Integer ownerId,Integer superb,Integer valid,
			Integer serial,Integer danmu,Integer moodFlag,Integer worldFlag,Integer maxId)throws Exception;
	
	/**
	 * 从opensearch查询标签。
	 * @param label
	 * @param jsonMap
	 * @throws Exception
	 */
	public JSONArray  queryOpChannelLabel(String label)throws Exception;
	
	/**
	 * 根据id查询频道
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public OpChannelV2Dto queryOpChannelById(Integer id)throws Exception;
}
