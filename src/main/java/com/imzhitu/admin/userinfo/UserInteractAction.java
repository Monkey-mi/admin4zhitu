package com.imzhitu.admin.userinfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.HTSException;
import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.UserReportDto;
import com.imzhitu.admin.userinfo.service.UserInteractService;

/**
 * <p>
 * 用户互动管理控制器
 * </p>
 * 
 * 创建时间：2013-9-12
 * 
 * @author ztj
 * 
 */
public class UserInteractAction extends BaseCRUDAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6448505129335869602L;

	private Integer userId;
	private String ids;
	private Integer count = 0;
	private String msg;
	private UserReportDto userReportDto = new UserReportDto();
	
	@Autowired
	private UserInteractService userInteractService;

	/**
	 * 查询粉丝列表
	 * 
	 * @return
	 */
	public String queryFollow() {
		try {
			userInteractService.buildFollow(userId, maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存粉丝
	 * 
	 * @return
	 */
	public String saveFollows() {
		try {
			if(!StringUtil.checkIsNULL(ids)) {
				userInteractService.saveFollows(ids, userId);
			} else if(count != null && count != 0) {
				userInteractService.saveRandomFollows(count, userId);
			} else {
				throw new Exception("未指定用户id或粉丝总数");
			}
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询关注列表
	 * 
	 * @return
	 */
	public String queryConcern() {
		try {
			userInteractService.buildConcern(userId, maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询用户举报
	 * 
	 * @return
	 */
	public String queryReport() {
		try {
			userInteractService.buildReport(userReportDto, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 跟进举报
	 * 
	 * @return
	 */
	public String followReport() {
		try {
			userInteractService.updateReportFollowed(ids);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	public UserInteractService getUserInteractService() {
		return userInteractService;
	}

	public void setUserInteractService(UserInteractService userInteractService) {
		this.userInteractService = userInteractService;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public UserReportDto getUserReportDto() {
		return userReportDto;
	}

	public void setUserReportDto(UserReportDto userReportDto) {
		this.userReportDto = userReportDto;
	}
	
	
}
