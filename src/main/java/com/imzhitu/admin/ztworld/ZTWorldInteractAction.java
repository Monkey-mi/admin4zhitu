package com.imzhitu.admin.ztworld;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.HTSException;
import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.ztworld.service.ZTWorldInteractService;

/**
 * <p>
 * 织图互动管理控制器
 * </p>
 * 
 * 创建时间：2013-8-9
 * @author ztj
 *
 */
public class ZTWorldInteractAction extends BaseCRUDAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7439251022516435459L;

	private Integer id;
	private Integer worldId;
	private String authorName;
	
	private String content;
	private Integer authorId;
	private Integer reId;
	private Integer reAuthorId;
	private Integer worldAuthorId;
	
	private String ids;
	private Integer count = 0;
	private String atIds;
	private String atNames;
	
	@Autowired
	private com.hts.web.ztworld.service.ZTWorldInteractService webWorldInteractService;
	@Autowired
	private ZTWorldInteractService worldInteractService;
	
	/**
	 * 查询评论列表
	 * 
	 * @return
	 */
	public String queryComment() {
		try {
			worldInteractService.buildComments(sinceId, maxId, 
					page, rows, worldId, authorName, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 屏蔽评论
	 * @return
	 */
	public String shieldComment() {
		try {
			worldInteractService.shieldComment(id);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 取消屏蔽评论
	 * @return
	 */
	public String unShieldComment() {
		try {
			worldInteractService.unShieldComment(id);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据用户id来屏蔽该用户的所有评论
	 * @return
	 */
	public String shieldCommentByUserId(){
		try {
			worldInteractService.updateCommentShieldByUserId(authorId, Tag.TRUE);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 解除该用户的所有评论屏蔽
	 * @return
	 */
	public String unShieldCommentByUserId(){
		try {
			worldInteractService.updateCommentShieldByUserId(authorId, Tag.FALSE);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存评论
	 * 
	 * @return
	 */
	public String saveComment() {
		try {
			if(reAuthorId == null || reAuthorId == 0) {
				webWorldInteractService.saveComment(false, worldId, worldAuthorId, authorId, content,
						null,null,jsonMap);
			} else {
				webWorldInteractService.saveReply(false, worldId, worldAuthorId, authorId, 
						content, reId, reAuthorId, null, null, jsonMap);
			}
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/*
	 * 织图喜欢用户子模块
	 */
	
	/**
	 * 查询喜欢用户列表
	 * 
	 * @return
	 */
	public String queryLikedUser() {
		try {
			worldInteractService.buildLikedUser(maxId, start, limit, worldId, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存喜欢用户
	 * 
	 * @return
	 */
	public String saveLikedUser() {
		try {
			if(!StringUtil.checkIsNULL(ids)) {
				worldInteractService.saveLikedUser(ids, worldId);
			} else if(count != 0){
				worldInteractService.saveLikedZombieUser(count, worldId);
			} else {
				throw new Exception("未指定用户id或喜欢总数");
			}
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/*
	 * 织图收藏子模块
	 */
	
	/**
	 * 查询喜欢用户列表
	 * 
	 * @return
	 */
	public String queryKeepUser() {
		try {
			worldInteractService.buildKeepUser(maxId, start, limit, worldId, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存喜欢用户
	 * 
	 * @return
	 */
	public String saveKeepUser() {
		try {
			if(!StringUtil.checkIsNULL(ids)) {
				worldInteractService.saveKeepUser(ids, worldId);
			} else if(count != 0){
				worldInteractService.saveKeepZombieUser(count, worldId);
			} else {
				throw new Exception("未指定用户id或收藏总数");
			}
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询举报
	 * 
	 * @return
	 */
	public String queryReport(){
		try {
			worldInteractService.queryReport(maxId,  page, rows, worldId,jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除举报
	 * @param ids
	 */
	public String deleteReportById() {
		try{
			worldInteractService.deleteReportById(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 全局过滤评论
	 * 
	 * @return
	 */
	public String trimComment() {
		try{
			worldInteractService.trimComment();
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 过滤回复
	 * 
	 * @return
	 */
	public String trimReply() {
		try{
			worldInteractService.trimReply();
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public com.hts.web.ztworld.service.ZTWorldInteractService getWebWorldInteractService() {
		return webWorldInteractService;
	}

	public void setWebWorldInteractService(
			com.hts.web.ztworld.service.ZTWorldInteractService webWorldInteractService) {
		this.webWorldInteractService = webWorldInteractService;
	}

	public ZTWorldInteractService getWorldInteractService() {
		return worldInteractService;
	}

	public void setWorldInteractService(ZTWorldInteractService worldInteractService) {
		this.worldInteractService = worldInteractService;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public Integer getReId() {
		return reId;
	}

	public void setReId(Integer reId) {
		this.reId = reId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getReAuthorId() {
		return reAuthorId;
	}

	public void setReAuthorId(Integer reAuthorId) {
		this.reAuthorId = reAuthorId;
	}

	public Integer getWorldAuthorId() {
		return worldAuthorId;
	}

	public void setWorldAuthorId(Integer worldAuthorId) {
		this.worldAuthorId = worldAuthorId;
	}

	public String getAtIds() {
		return atIds;
	}

	public void setAtIds(String atIds) {
		this.atIds = atIds;
	}

	public String getAtNames() {
		return atNames;
	}

	public void setAtNames(String atNames) {
		this.atNames = atNames;
	}
	
}
