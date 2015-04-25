package com.imzhitu.admin.plat;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.plat.service.PlatService;

public class PlatAction extends BaseCRUDAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 834069158059009986L;

	private String cid;
	private String cname;
	private Integer pid;
	private Integer index;

	@Autowired
	private PlatService platService;

	/**
	 * 查询被关注列表
	 * 
	 * @return
	 */
	public String queryBeConcern() {
		try {
			platService.buildBeConcern(jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 保存被关注用户
	 * 
	 * @return
	 */
	public String saveBeConcern() {
		try {
			platService.saveBeConcern(cid, cname, pid);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除被关注
	 * 
	 * @return
	 */
	public String deleteBeConcern() {
		try {
			platService.deleteBeConcern(index);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

}
