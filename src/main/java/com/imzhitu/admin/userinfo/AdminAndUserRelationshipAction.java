package com.imzhitu.admin.userinfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.userinfo.service.AdminAndUserRelationshipService;

/**
 * 管理员账号与织图用户绑定控制器
 * 
 * @author zhangbo 2015-05-13
 *
 */
public class AdminAndUserRelationshipAction extends BaseCRUDAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 7852903350339839379L;

	private Integer id; // 管理员账号与织图用户关联关系表主键id

	private Integer userId; // 用户主键id

	private String ids; // 管理员账号与织图用户关联关系表主键id集合，由前台传入

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	@Autowired
	AdminAndUserRelationshipService adminUserRelationship;

	/**
	 * 创建管理员账号与织图用户的关联关系
	 * 
	 * @return
	 * @author zhangbo 2015年5月14日
	 */
	public String createAdminAndUserRelationship() {
		try {
			// 创建关联关系
			adminUserRelationship.createAdminAndUserRelationship(getCurrentLoginUserId(), userId);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 更新管理员账号与织图用户的关联关系
	 *
	 * @return
	 * @author zhangbo 2015年5月18日
	 */
	public String updateAdminAndUserRelationship() {
		try {
			// 更新关联关系
			adminUserRelationship.updateAdminAndUserRelationship(id, userId);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 查询管理员账号与织图用户关联关系
	 *
	 * @return
	 * @author zhangbo 2015年5月19日
	 */
	public String queryAdminAndUserRelationship() {
		try {
			adminUserRelationship.queryAdminAndUserRelationship(getCurrentLoginUserId(), maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		;
		return StrutsKey.JSON;
	}

	/**
	 * 删除管理员账号与织图用户关联关系
	 *
	 * @return
	 * @author zhangbo 2015年5月19日
	 */
	public String deleteAdminAndUserRelationship() {
		try {
			adminUserRelationship.deleteAdminAndUserRelationship(StringUtil.convertStringToIds(ids));

			// 返回删除成功信息
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		;
		return StrutsKey.JSON;
	}

}
