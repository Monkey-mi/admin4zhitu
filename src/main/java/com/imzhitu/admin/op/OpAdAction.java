package com.imzhitu.admin.op;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.op.service.OpAdService;

public class OpAdAction extends BaseCRUDAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3942016121684250668L;

	private String ids;
	private Integer phoneCode = Tag.IOS;
	private String appName;
	private String appIcon;
	private String appLink;
	private String appDesc;
	private Integer appId;
	private Integer open = Tag.FALSE;
	private String json;
	
	@Autowired
	private OpAdService opAdService;
	
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Integer getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(Integer phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getAppIcon() {
		return appIcon;
	}

	public void setAppIcon(String appIcon) {
		this.appIcon = appIcon;
	}

	public String getAppLink() {
		return appLink;
	}

	public void setAppLink(String appLink) {
		this.appLink = appLink;
	}

	public String getAppDesc() {
		return appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	public OpAdService getOpAdService() {
		return opAdService;
	}

	public void setOpAdService(OpAdService opAdService) {
		this.opAdService = opAdService;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
	public Integer getOpen() {
		return open;
	}

	public void setOpen(Integer open) {
		this.open = open;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * 保存APP链接
	 */
	public String saveAppLink() {
		try {
			opAdService.saveAppLink(appName, appIcon, appDesc, appLink, phoneCode, open);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询APP链接
	 * 
	 * @return
	 */
	public String queryAppLink() {
		try {
			opAdService.buildAppLink(open, phoneCode, maxSerial, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新APP链接排序
	 * 
	 * @return
	 */
	public String updateAppLinkSerial() {
		String[] ids = request.getParameterValues("reIndexId");
		try {
			opAdService.updateAppLinkSerial(ids);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据ids删除链接
	 * 
	 * @return
	 */
	public String deleteAppLinks() {
		try {
			opAdService.deleteAppLink(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
		
	}
	
	/**
	 * 根据JSON批量更新链接
	 * 
	 * @return
	 */
	public String updateAppLinkByJSON() {
		try {
			opAdService.updateAppLinkByJSON(json);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询点击记录
	 * @return
	 */
	public String queryAppLinkRecord() {
		try {
			opAdService.buildAppLinkRecord(appId, maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

}
