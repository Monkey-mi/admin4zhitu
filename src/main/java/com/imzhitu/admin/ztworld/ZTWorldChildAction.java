package com.imzhitu.admin.ztworld;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.pojo.HTWorldChildWorldTypeDto;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.ztworld.service.ZTWorldChildWorldService;

/**
 * <p>
 * 织图子世界Action
 * </p>
 * 
 * 创建时间：2014-6-13
 * @author tianjie
 *
 */
public class ZTWorldChildAction extends BaseCRUDAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7704915929429920288L;
	
	private Integer id;
	private String ids;
	private String typePath;
	private Integer total;
	private Integer useCount;
	private String typeDesc;
	private String descPath;
	private String labelName;
	private Integer serial;
	
	@Autowired
	private ZTWorldChildWorldService worldChildWorldService;
	
	
	/**
	 * 查询子世界类型列表
	 * 
	 * @return
	 */
	public String queryChildType() {
		try {
			worldChildWorldService.buildChildType(maxSerial, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存子世界类型
	 * 
	 * @return
	 */
	public String saveChildType() {
		try {
			worldChildWorldService.saveChildType(typePath, total, useCount, 
					typeDesc, descPath,labelName);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除子世界类型
	 * 
	 * @return
	 */
	public String deleteChildType() {
		try {
			worldChildWorldService.deleteChildType(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新子世界类型序号
	 * 
	 * @return
	 */
	public String updateChildTypeSerial() {
		String[] ids = request.getParameterValues("reIndexId");
		try {
			worldChildWorldService.updateChildTypeSerial(ids);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新最新子世界类型
	 * 
	 * @return
	 */
	public String updateLatestChildType() {
		try {
			worldChildWorldService.updateLatestChildType(limit);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据id查询子世界类型
	 * 
	 * @return
	 */
	public String queryChildTypeById() {
		try {
			HTWorldChildWorldTypeDto dto = worldChildWorldService.getChildTypeById(id);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, dto, OptResult.JSON_KEY_TYPE, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新子世界类型
	 * 
	 * @return
	 */
	public String updateChildType() {
		try {
			worldChildWorldService.updateChildType(id, typePath, total, useCount,
					typeDesc, descPath, labelName, serial);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public ZTWorldChildWorldService getWorldChildWorldService() {
		return worldChildWorldService;
	}

	public void setWorldChildWorldService(
			ZTWorldChildWorldService worldChildWorldService) {
		this.worldChildWorldService = worldChildWorldService;
	}

	public String getTypePath() {
		return typePath;
	}

	public void setTypePath(String typePath) {
		this.typePath = typePath;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
	public Integer getUseCount() {
		return useCount;
	}

	public void setUseCount(Integer useCount) {
		this.useCount = useCount;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public String getDescPath() {
		return descPath;
	}

	public void setDescPath(String descPath) {
		this.descPath = descPath;
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

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	
	
}
