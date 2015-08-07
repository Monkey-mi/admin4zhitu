package com.imzhitu.admin.interact;


import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.interact.service.InteractChannelWorldLabelService;
import com.imzhitu.admin.interact.service.InteractWorldCommentLabelService;
import com.imzhitu.admin.interact.service.InteractWorldLabelService;

/**
 * 
 * @author zxx 2015年7月20日 16:38:46
 *
 */
public class InteractWorldLabelAction extends BaseCRUDAction{

	private static final long serialVersionUID = -1574906282888550179L;
	
	@Autowired
	private InteractChannelWorldLabelService channelWorldLabelService;
	
	@Autowired
	private InteractWorldLabelService worldLabelService;
	
	@Autowired
	private InteractWorldCommentLabelService worldCommentLabelService;
	
	private Integer channelId;
	private Integer worldId;
	private String labelIds;

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public void setLabelIds(String labelIds) {
		this.labelIds = labelIds;
	}

	public String insertChannelWorldLabel(){
		try{
			channelWorldLabelService.insertChannelWorldLabel(channelId, worldId, labelIds, getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String queryChannelWorldLabel(){
		try{
			channelWorldLabelService.queryChannelWorldLabel( worldId, channelId,jsonMap);
			JSONUtil.optSuccess(OptResult.QUERY_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 获取分享列表织图对应展示的快速评论点赞浏览标签集合
	 * 
	 * @return
	 * @author zhangbo	2015年8月6日
	 */
	public String queryWorldLabelList() {
		try{
			worldLabelService.queryWorldLabels(worldId, jsonMap);
			JSONUtil.optSuccess(OptResult.QUERY_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 存储织图被选中的评论标签
	 * 
	 * @return
	 * @author zhangbo	2015年8月6日
	 */
	public String insertWorldLabel() {
		try{
			worldCommentLabelService.insertWorldCommentLabel(worldId, labelIds, getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
}
