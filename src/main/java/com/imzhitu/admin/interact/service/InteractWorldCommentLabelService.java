package com.imzhitu.admin.interact.service;

import java.util.List;

import com.imzhitu.admin.common.pojo.InteractWorldCommentLabel;

public interface InteractWorldCommentLabelService {

	/**
	 * 记录织图被打上评论标签
	 * 
	 * @param worldId	织图id
	 * @param labelIds	评论标签集合，以","分隔的字符串
	 * @param operator	当前登陆admin用户的id
	 * @throws Exception
	 * @author zhangbo	2015年8月7日
	 */
	public void insertWorldCommentLabel(Integer worldId,String labelIds, Integer operator)throws Exception;
	
	/**
	 * 查询
	 * @param worldId
	 * @throws Exception
	 */
	public List<InteractWorldCommentLabel> queryWorldCommentLabel(Integer worldId)throws Exception;
}
