package com.imzhitu.admin.interact.service;

import java.util.List;
import java.util.Map;

import com.imzhitu.admin.common.pojo.InteractCommentLabelChannel;

public interface InteractCommentLabelChannelService {
	/**
	 * 添加
	 * @param channelId
	 * @param commentLabelId
	 * @param operator
	 * @throws Exception
	 */
	public void insertCommentLabelChannel(Integer channelId,Integer commentLabelId,Integer operator)throws Exception;
	
	/**
	 * 批量删除
	 * @param idsStr
	 * @throws Exception
	 */
	public void batchDeleteCommentLabelChannel(String idsStr)throws Exception;
	
	/**
	 * 分页查询
	 * @param id
	 * @param channelId
	 * @param commentLabelId
	 * @param maxId
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryCommentLabelChannel(Integer id,Integer channelId,Integer commentLabelId,Integer maxId,int page,
			int rows,Map<String,Object>jsonMap)throws Exception;
	
	/**
	 * 分页查询总数
	 * @param id
	 * @param channelId
	 * @param commentLabelId
	 * @return
	 * @throws Exception
	 */
	public long queryComemntLabelChannelTotalCount(Integer id,Integer channelId,Integer commentLabelId,Integer maxId)throws Exception;
	
	/**
	 * 根据频道Id查询评论标签
	 * 
	 * @param channelId
	 * @return
	 * @throws Exception
	 */
	public List<InteractCommentLabelChannel> queryCommentLabelByChannelId(Integer channelId)throws Exception;
	
	/**
	 * 由于展示的需要，所以根据标签id，查询标签名字
	 * @param label_ids
	 * @return
	 * @throws Exception
	 */
	public List<InteractCommentLabelChannel> queryCommentLabelNameByLabelIds(String label_ids)throws Exception;
}
