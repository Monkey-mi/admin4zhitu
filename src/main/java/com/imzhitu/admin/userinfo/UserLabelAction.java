package com.imzhitu.admin.userinfo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONArray;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.UserSexLabelDto;
import com.imzhitu.admin.userinfo.service.UserLabelService;

/**
 * <p>
 * 用户标签管理Action控制器
 * </p>
 * 
 * 创建时间：2014-2-5
 * 
 * @author tianjie
 * 
 */
public class UserLabelAction extends BaseCRUDAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9052360580678870968L;
	
	private Integer userId;
	private String labelIds;
	private String labels;
	private Boolean display = true;
	
	@Autowired
	private UserLabelService userLabelService;

	public UserLabelService getUserLabelService() {
		return userLabelService;
	}
	
	public void setUserLabelService(UserLabelService userLabelService) {
		this.userLabelService = userLabelService;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getLabelIds() {
		return labelIds;
	}

	public void setLabelIds(String labelIds) {
		this.labelIds = labelIds;
	}

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}
	
	public Boolean getDisplay() {
		return display;
	}

	public void setDisplay(Boolean display) {
		this.display = display;
	}

	/**
	 * 根据用户id查询标签id
	 * @return
	 */
	public String queryLabelIdByUserId() {
		try {
			userLabelService.buildLabelId(userId, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存标签用户关联关系
	 * 
	 * @return
	 */
	public String saveLabelUser() {
		try {
			userLabelService.saveLabelUser(userId, labelIds, labels, display);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	/**
	 * 查询用户标签树
	 * 
	 * @return
	 */
	public String queryUserLabelTree() {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			List<UserSexLabelDto> labels = userLabelService.getAllUserLabel();
			JSONArray jsArray = JSONArray.fromObject(labels);
			out.print(jsArray.toString());
		} catch (IOException e) {
		} catch (Exception e) {
		} finally {
			out.close();
		}
		return null;
	}
}
