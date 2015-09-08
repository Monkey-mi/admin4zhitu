package com.imzhitu.admin.op;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.OpAdAppLink;
import com.imzhitu.admin.common.pojo.OpAdAppLinkRecord;
import com.imzhitu.admin.op.service.OpAdService;

/**
 * <p>
 * 广告运营控制器
 * </p>
 * 
 * 创建时间: 2015-09-07
 * @author lynch
 *
 */
public class OpAdAction extends BaseCRUDAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3942016121684250668L;

	private String ids;
	private Integer appId;
	private Integer id;
	private Integer valid;
	private OpAdAppLink link = new OpAdAppLink();
	private OpAdAppLinkRecord record = new OpAdAppLinkRecord();
	
	@Autowired
	private OpAdService opAdService;
	
	/**
	 * 保存APP链接
	 */
	public String saveAppLink() {
		try {
			opAdService.saveAppLink(link);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新链接
	 * 
	 * @return
	 */
	public String updateAppLink() {
		try {
			opAdService.updateAppLink(link);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
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
			opAdService.buildAppLink(link, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据id查询App链接
	 * 
	 * @return
	 */
	public String queryAppLinkById() {
		try {
			opAdService.buildAppLinkById(id, jsonMap);
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
	 * 查询点击记录
	 * @return
	 */
	public String queryAppLinkRecord() {
		try {
			opAdService.buildAppLinkRecord(record, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新有效性
	 * 
	 * @return
	 */
	public String updateAppLinkValid() {
		try {
			opAdService.updateAppLinkValid(ids, valid);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public OpAdAppLink getLink() {
		return link;
	}

	public void setLink(OpAdAppLink link) {
		this.link = link;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public OpAdAppLinkRecord getRecord() {
		return record;
	}

	public void setRecord(OpAdAppLinkRecord record) {
		this.record = record;
	}
	
}
