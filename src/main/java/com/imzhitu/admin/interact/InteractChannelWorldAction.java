package com.imzhitu.admin.interact;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.interact.service.CommentService;
import com.imzhitu.admin.interact.service.InteractChannelWorldService;

/**
 * 频道织图互动操作
 * 
 * @author zhangbo	2015年10月28日
 *
 */
public class InteractChannelWorldAction extends BaseCRUDAction {

	/**
	 * @author zhangbo	2015年10月28日
	 */
	private static final long serialVersionUID = -5165967332697709803L;
	
	/**
	 * 频道id
	 * @author zhangbo	2015年10月30日
	 */
	private Integer channelId;
	
	/**
	 * 织图id
	 * @author zhangbo	2015年10月29日
	 */
	private Integer worldId;
	
	/**
	 * 评论id集合，以逗号分隔
	 * @author zhangbo	2015年10月30日
	 */
	private String commentIds;
	
	/**
	 * 未生成的评论字符串（由换行符\n作为分隔符，分成不同的评论）
	 * @author zhangbo	2015年10月29日
	 */
	private String commentStrs;
	
	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	/**
	 * @param worldId the worldId to set
	 */
	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	/**
	 * @param commentIds the commentIds to set
	 */
	public void setCommentIds(String commentIds) {
		this.commentIds = commentIds;
	}

	/**
	 * @param commentStrs the commentStrs to set
	 */
	public void setCommentStrs(String commentStrs) {
		this.commentStrs = commentStrs;
	}

	@Autowired
	private InteractChannelWorldService interactChannelWorldService;
	
	@Autowired
	private CommentService commentService;
	
	/**
	 * 添加频道织图互动
	 * 
	 * @return
	 * @author zhangbo	2015年10月31日
	 */
	public String addChannelWorldLevelList(){
		try{
			Integer[] commentId = StringUtil.convertStringToIds(commentIds);
			
			// 保存频道织图互动评论
			interactChannelWorldService.saveChannelWorldInteractComment(channelId, worldId, commentId);
			
			if(commentStrs != null && !"".equals(commentStrs.trim())){
				String[] comments = commentStrs.split("\n");
				
				Integer[] commentIdArray = new  Integer[1];
				List<Integer> commentIdList = new ArrayList<Integer>();

				
				for(String str:comments){
					if(!"".equals(str.trim())) {
						// 保存评论到“其他”分类下，labelId=5，目前先写死
						Integer commentId1 = commentService.saveComment(str, 5);
						commentIdList.add(commentId1);
					}
				}
				commentIdArray = commentIdList.toArray(commentIdArray);  
				// 保存频道织图互动评论
				interactChannelWorldService.saveChannelWorldInteractComment(channelId, worldId, commentIdArray);
			}
			
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询还未开始互动的点赞、播放、评论数
	 * 
	 * @return
	 * @author zhangbo	2015年10月31日
	 */
	public String queryChannelWorldUNInteractCount() {
		try{
			Map<String, Integer> resultMap = interactChannelWorldService.queryWorldUNInteractCount(channelId, worldId);
			
			jsonMap.put("clickCount", resultMap.get("clickCount"));
			jsonMap.put("likedCount", resultMap.get("likedCount"));
			jsonMap.put("commentCount", resultMap.get("commentCount"));
			
			JSONUtil.optSuccess(OptResult.QUERY_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

}
