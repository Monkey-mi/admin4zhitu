package com.imzhitu.admin.userinfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.pojo.UserVerify;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.userinfo.service.UserVerifyService;

/**
 * <p>
 * 用户认证模块Action
 * </p>
 * 
 * 创建时间：2014-7-16
 * @author tianjie
 *
 */
public class UserVerifyAction extends BaseCRUDAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -581261755552831040L;
	
	@Autowired
	private UserVerifyService userVerifyService;
	
	private Integer id;
	private String ids;
	private Integer serial;
	private String verifyName;
	private String verifyDesc;
	private String verifyIcon;
	private Boolean addAllTag = false;
	
	/**
	 * 查询认证信息
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String queryVerify() {
		try {
			userVerifyService.buildVerify(maxSerial, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询所有认证信息
	 * 
	 * @return
	 */
	public String queryAllVerify() {
		try {
			userVerifyService.buildVerify(addAllTag, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据id查询认证信息
	 * 
	 * @return
	 */
	public String queryVerifyById() {
		try {
			UserVerify verify = userVerifyService.queryVerify(id);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, verify, OptResult.JSON_KEY_VERIFY, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新认证信息
	 * 
	 * @return
	 */
	public String updateVerify() {
		try {
			userVerifyService.updateVerify(id, verifyName, verifyDesc, verifyIcon, serial);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存认证信息
	 * 
	 * @return
	 */
	public String saveVerify() {
		try {
			userVerifyService.saveVerify(verifyName, verifyDesc, verifyIcon);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除认证信息
	 * 
	 * @return
	 */
	public String deleteVerify() {
		try {
			userVerifyService.deleteVerify(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optSuccess(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新认证信息排序
	 * 
	 * @return
	 */
	public String updateVerifySerial() {
		String[] ids = request.getParameterValues("reIndexId");
		try {
			userVerifyService.updateVerifySerial(ids);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optSuccess(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新认证列表缓存
	 * 
	 * @return
	 */
	public String updateVerifyCache() {
		try {
			userVerifyService.updateVerifyCache(limit);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optSuccess(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	public String getVerifyName() {
		return verifyName;
	}

	public void setVerifyName(String verifyName) {
		this.verifyName = verifyName;
	}

	public String getVerifyDesc() {
		return verifyDesc;
	}

	public void setVerifyDesc(String verifyDesc) {
		this.verifyDesc = verifyDesc;
	}

	public String getVerifyIcon() {
		return verifyIcon;
	}

	public void setVerifyIcon(String verifyIcon) {
		this.verifyIcon = verifyIcon;
	}

	public Boolean getAddAllTag() {
		return addAllTag;
	}

	public void setAddAllTag(Boolean addAllTag) {
		this.addAllTag = addAllTag;
	}
	
	
	
}
