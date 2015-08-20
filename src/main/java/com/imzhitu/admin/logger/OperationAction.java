package com.imzhitu.admin.logger;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.LoggerOperation;
import com.imzhitu.admin.logger.service.OperationService;

public class OperationAction extends BaseCRUDAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1699936297217687511L;
	
	private String ids;
	private Integer id;
	private String optInterface;
	private String optName;
	private String optDesc;
	private Integer serial;
	
	private Integer userId;
	private Integer optId;
	private Boolean addAllTag = false;
	private Date startDate;
	private Date endDate;
	
	@Autowired
	private OperationService loggerOperationService;

	/**
	 * 查询操作信息
	 * 
	 * @return
	 */
	public String queryOperation() {
		try {
			loggerOperationService.buildOperation(maxSerial, addAllTag,  page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据id查询操作信息
	 * 
	 * @return
	 */
	public String queryOperationById() {
		try {
			LoggerOperation opt = loggerOperationService.getOperationById(id);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, opt, OptResult.JSON_KEY_MSG, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存操作信息
	 * 
	 * @return
	 */
	public String saveOperation() {
		try {
			loggerOperationService.saveOperation(id, optInterface, optName, optDesc);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新操作信息
	 * 
	 * @return
	 */
	public String updateOperation() {
		try {
			loggerOperationService.updateOperation(id, optInterface, optName, optDesc, serial);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return StrutsKey.JSON;
	}
	
	
	/**
	 * 删除操作信息
	 * 
	 * @return
	 */
	public String deleteOperation() {
		try {
			loggerOperationService.deleteOperation(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新操作排序
	 * 
	 * @return
	 */
	public String updateOperationSerial() {
		String[] ids = request.getParameterValues("reIndexId");
		try {
			loggerOperationService.updateOperationSerial(ids);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询用户操作信息
	 * 
	 * @return
	 */
	public String queryUserOperation() {
		if(userId == null) {
			userId = getCurrentLoginUserId();
		}
		try {
			loggerOperationService.buildUserOperation(maxId, page, rows, userId, optId, startDate, endDate, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public OperationService getLoggerOperationService() {
		return loggerOperationService;
	}

	public void setLoggerOperationService(
			OperationService loggerOperationService) {
		this.loggerOperationService = loggerOperationService;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOptInterface() {
		return optInterface;
	}

	public void setOptInterface(String optInterface) {
		this.optInterface = optInterface;
	}

	public String getOptName() {
		return optName;
	}

	public void setOptName(String optName) {
		this.optName = optName;
	}

	public String getOptDesc() {
		return optDesc;
	}

	public void setOptDesc(String optDesc) {
		this.optDesc = optDesc;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getOptId() {
		return optId;
	}

	public void setOptId(Integer optId) {
		this.optId = optId;
	}

	public Boolean getAddAllTag() {
		return addAllTag;
	}

	public void setAddAllTag(Boolean addAllTag) {
		this.addAllTag = addAllTag;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}

