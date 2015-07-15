package com.imzhitu.admin.interact;

import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONObject;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.InteractUser;
import com.imzhitu.admin.interact.service.InteractWorldService;

/**
 * <p>
 * 活动维护控制器
 * </p>
 * 
 * 创建时间：2014-2-26
 * @author tianjie
 *
 */
public class InteractAction extends BaseCRUDAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 926297376116962121L;
	
	private String ids;
	private Integer interactId;
	private Integer worldId;
	private Integer labelId;
	private Integer clickCount = 0;
	private Integer likedCount = 0;
	private Integer commentCount = 0;
	private String comments;
	private Integer duration = 2;
	private Integer followCount = 0;
	private Integer userId;
	private String interactWorldCommentIds;
	
	@Autowired
	private InteractWorldService interactWorldService;
	
	/**
	 * 查询互动列表
	 * 
	 * @return
	 */
	public String queryInteractList() {
		try {
			interactWorldService.buildInteracts(worldId, maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询互动总数
	 * 
	 * @return
	 */
	public String queryInteractSum() {
		try {
			interactWorldService.buildInteractSum(worldId, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询互动总数
	 * 
	 * @return
	 */
	public String queryInteractSum4Callback() {
		PrintWriter out = null;
		String jsonCallback = request.getParameter("callback");
		try {
			out = response.getWriter();
			interactWorldService.buildInteractSum(worldId, jsonMap);
			JSONUtil.optSuccess(jsonMap);
			JSONObject json = JSONObject.fromObject(jsonMap);
			out.print(jsonCallback + "(" +json.toString() + ")");
			out.flush();
		} catch(Exception e) {
			e.printStackTrace();
			jsonMap.clear();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			JSONObject json = JSONObject.fromObject(jsonMap);
			out.print(jsonCallback + "(" +json.toString() + ")");
			out.flush();
		} finally {
			out.close();
		}
		return null;
	}
	
	/**
	 * 根据织图id查询互动标志
	 * 
	 * @return
	 */
	public String queryInteractByWorldIds() {
		try {
			if(ids != null && !StringUtil.checkIsNULL(ids)) {
				interactWorldService.buildInteractByWorldIds(ids, jsonMap);
			}
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存互动信息
	 * @return
	 */
	public String saveInteract() {
		try {
			String[] commentIds = request.getParameterValues("comments");
			interactWorldService.saveInteractV3(worldId, clickCount, likedCount, commentIds, duration);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 获取评论互动列表
	 * 
	 * @return
	 */
	public String queryCommentList() {
		try {
			interactWorldService.buildComments(interactId, maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 获取喜欢互动列表
	 * 
	 * @return
	 */
	public String queryLikedList() {
		try {
			interactWorldService.buildLikeds(interactId, maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 获取播放互动列表
	 * 
	 * @return
	 */
	public String queryClickList() {
		try {
			interactWorldService.buildClicks(interactId, maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 刷新所有互动
	 * @param maxId
	 * @return
	 */
	public String refreshInteract() {
		try {
			interactWorldService.updateInteractValid(maxId);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除互动
	 * 
	 * @return
	 */
	public String deleteInteract() {
		try {
			interactWorldService.deleteInteract(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询用户互动列表
	 * 
	 * @return
	 */
	public String queryUserInteractList() {
		try {
			interactWorldService.buildUserInteract(maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据用户id查询互动
	 * 
	 * @return
	 */
	public String queryUserInteractByUID() {
		try {
			InteractUser interact = interactWorldService.getUserInteractByUID(userId);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, interact, 
					OptResult.JSON_KEY_USER_INFO, jsonMap);
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 保存用户互动
	 * 
	 * @return
	 */
	public String saveUserInteract() {
		try {
			interactWorldService.saveUserInteract(userId, followCount, duration);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除用户互动
	 * @return
	 */
	public String deleteUserInteract() {
		try {
			interactWorldService.deleteUserInteract(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 构建粉丝列表
	 * 
	 * @return
	 */
	public String queryFollowList() {
		try {
			interactWorldService.buildFollow(interactId, maxId, start, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询跟踪器列表
	 * 
	 * @return
	 */
	public String queryTrackerList() {
		try {
			interactWorldService.buildTracker(jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	/**
	 * 根据织图id查询互动id
	 */
	public String queryIntegerIdByWorldId(){
		try {
			Integer iId = interactWorldService.queryIntegerIdByWorldId(worldId);
			JSONUtil.optResult(OptResult.OPT_SUCCESS,iId,"interactId", jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除互动评论by ids
	 * @return
	 */
	public String deleteInteractCommentByIds(){
		try {
			interactWorldService.deleteInteractCommentByids(interactWorldCommentIds);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS,jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public InteractWorldService getInteractWorldService() {
		return interactWorldService;
	}

	public void setInteractWorldService(InteractWorldService interactWorldService) {
		this.interactWorldService = interactWorldService;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	public Integer getLikedCount() {
		return likedCount;
	}

	public void setLikedCount(Integer likedCount) {
		this.likedCount = likedCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}


	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public Integer getLabelId() {
		return labelId;
	}

	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}

	public Integer getInteractId() {
		return interactId;
	}

	public void setInteractId(Integer interactId) {
		this.interactId = interactId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getFollowCount() {
		return followCount;
	}

	public void setFollowCount(Integer followCount) {
		this.followCount = followCount;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
	public void setInteractWorldCommentIds(String interactWorldCommentIds){
		this.interactWorldCommentIds = interactWorldCommentIds;
	}
	
	public String getInteractWorldCommentIds(){
		return this.interactWorldCommentIds;
	}
	
	
}
