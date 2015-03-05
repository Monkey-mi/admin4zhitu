package com.imzhitu.admin.interact;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.interact.service.InteractAutoResponseSchedulaService;

public class InteractAutoResponseSchedulaAction extends BaseCRUDAction{
	private static final long serialVersionUID = 308225301713662112L;
	private Date addDate;
	private Date modifyDate;
	private Date schedula;
	private Integer valid;
	private Integer complete;
	private Integer id;
	private Integer autoResponseId;
	private String ids;
	@Autowired
	private InteractAutoResponseSchedulaService interactAutoResponseSchedulaService;
	
	/**
	 * 分页查询
	 * @return
	 */
	public String queryAutoResponseSchedulaForTable(){
		try{
			interactAutoResponseSchedulaService.queryAutoResponseSchedulaForTable(addDate, modifyDate, schedula,autoResponseId, valid, complete,page,rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	/**
	 * 批量删除
	 * @return
	 */
	public String delAutoResponseByIds(){
		try{
			interactAutoResponseSchedulaService.delInteractAutoResponseSchedula(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS,jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(OptResult.DELETE_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public Date getSchedula() {
		return schedula;
	}
	public void setSchedula(Date schedula) {
		this.schedula = schedula;
	}
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
	}
	public Integer getComplete() {
		return complete;
	}
	public void setComplete(Integer complete) {
		this.complete = complete;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getAutoResponseId() {
		return autoResponseId;
	}

	public void setAutoResponseId(Integer autoResponseId) {
		this.autoResponseId = autoResponseId;
	}


	public String getIds() {
		return ids;
	}


	public void setIds(String ids) {
		this.ids = ids;
	}
	
}
