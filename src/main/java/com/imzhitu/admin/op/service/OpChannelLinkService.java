package com.imzhitu.admin.op.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;

/**
 * 
 * @author zxx
 *
 */
public interface OpChannelLinkService  extends BaseService{
	/**
	 * 增加
	 * @param channelId
	 * @param linkId
	 * @throws Exception
	 */
	public void insertOpChannelLink(Integer channelId,Integer linkId)throws Exception;
	
	/**
	 * 删除
	 * @param channelId
	 * @param linkId
	 * @throws Exception
	 */
	public void deleteOpChannelLink(Integer channelId,Integer linkId)throws Exception;
	
	/**
	 * 分页查询
	 * @param maxId
	 * @param channelId
	 * @param linkId
	 * @return
	 * @throws Exception
	 */
	public long queryOpChannelLinkTotalCount(Integer maxId,Integer channelId,Integer linkId)throws Exception;
	
	/**
	 * 分页查询总数
	 * @param maxId
	 * @param page
	 * @param rows
	 * @param channelId
	 * @param linkId
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryOpChannelLink(Integer maxId,int page,int rows ,Integer channelId,Integer linkId,Map<String,Object>jsonMap)throws Exception;
	
	/**
	 * 批量删除
	 * @param rowJson
	 * @throws Exception
	 */
	public void batchDeleteOpChannelLink(String rowJson)throws Exception;
}
