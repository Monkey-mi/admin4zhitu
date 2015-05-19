package com.imzhitu.admin.op;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.OpSysDanmu;
import com.imzhitu.admin.op.service.DanmuService;

public class DanmuAction extends BaseCRUDAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6000545782769800822L;

	private OpSysDanmu sysDanmu = new OpSysDanmu();
	
	private String ids;
	private Integer id;
	private File sysDanmuFile;
	private Integer channelId;
	
	@Autowired
	private DanmuService danmuService;

	/**
	 * 查询系统弹幕
	 * 
	 * @return
	 */
	public String querySysDanmu() {
		try {
			danmuService.buildSysDanmu(sysDanmu, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存系统弹幕
	 * 
	 * @return
	 */
	public String saveSysDanmu() {
		try {
			danmuService.saveSysDanmu(sysDanmu);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据文件保存系统弹幕
	 * 
	 * @return
	 */
	public String saveSysDanmuByFile() {
		try {
			danmuService.saveSysDanmu(channelId, sysDanmuFile);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据id查询系统弹幕
	 * 
	 * @return
	 */
	public String querySysDanmuById() {
		try {
			OpSysDanmu danmu = danmuService.querySysDanmuById(id);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, danmu, OptResult.JSON_KEY_OBJ, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新系统弹幕
	 * 
	 * @return
	 */
	public String updateSysDanmu() {
		try {
			danmuService.updateSysDanum(sysDanmu);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新系统弹幕顺序
	 * 
	 * @return
	 */
	public String updateSysDanmuSerial() {
		String[] ids = request.getParameterValues("reIndexId");
		try {
			danmuService.updateSysDanmuSerial(ids);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据id删除系统弹幕
	 * 
	 * @return
	 */
	public String deleteSysDanmus() {
		try {
			danmuService.deleteSysDanmu(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	public OpSysDanmu getSysDanmu() {
		return sysDanmu;
	}

	public void setSysDanmu(OpSysDanmu sysDanmu) {
		this.sysDanmu = sysDanmu;
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

	public File getSysDanmuFile() {
		return sysDanmuFile;
	}

	public void setSysDanmuFile(File sysDanmuFile) {
		this.sysDanmuFile = sysDanmuFile;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	
}
