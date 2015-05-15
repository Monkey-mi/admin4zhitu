package com.imzhitu.admin.userinfo;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.userinfo.service.impl.UserInfoServiceImpl;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.userinfo.service.AdminAndUserRelationshipService;

/**
 * 管理员账号与织图用户绑定控制器
 * 
 * @author zhangbo 2015-05-13
 *
 */
public class AdminAndUserRelationshipAction extends BaseCRUDAction{

    /**
     * 序列号
     */
    private static final long serialVersionUID = 7852903350339839379L;
    
    private Integer id;		// 管理员账号与织图用户关联关系表主键id
    
    private Integer adminId;	// 管理员账号主键id
    
    private Integer userId;	// 用户主键id
    
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the adminId
     */
    public Integer getAdminId() {
        return adminId;
    }

    /**
     * @param adminId the adminId to set
     */
    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    /**
     * @return the userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
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
	    boolean result = adminUserRelationship.createAdminAndUserRelationship(getCurrentLoginUserId(), getUserId());
	    
	    if ( result ) {
		// 返回创建成功信息
		JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
	    } else {
		// 返回创建成功信息
		JSONUtil.optFailed("绑定的用户Id不存在，请填写正确的用户Id", jsonMap);
	    }
	} catch (Exception e) {
	    JSONUtil.optFailed(e.getMessage(), jsonMap);
	};
	return StrutsKey.JSON;
    }
    
    public String updateAdminAndUserRelationship() {
	try {
//	    jsonMap = adminUserRelationship.createAdminAndUserRelationship();
	} catch (Exception e) {
	    JSONUtil.optFailed(e.getMessage(), jsonMap);
	};
	return StrutsKey.JSON;
    }
    
    public String queryAdminAndUserRelationship() {
	try {
	    jsonMap = adminUserRelationship.queryAdminAndUserRelationship(getCurrentLoginUserId());
	} catch (Exception e) {
	    JSONUtil.optFailed(e.getMessage(), jsonMap);
	};
	return StrutsKey.JSON;
    }
    
    public String deleteAdminAndUserRelationship() {
	try {
//	    jsonMap = adminUserRelationship.createAdminAndUserRelationship();
	} catch (Exception e) {
	    JSONUtil.optFailed(e.getMessage(), jsonMap);
	};
	return StrutsKey.JSON;
    }

}
