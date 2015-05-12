package com.imzhitu.admin.ztworld;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.HTWorldLabel;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.ztworld.service.ZTWorldLabelService;

/**
 * <p>
 * 织图标签管理控制器
 * <p>
 * 
 * 创建时间：2014-5-7
 * @author tianjie
 *
 */
public class ZTWorldLabelAction extends BaseCRUDAction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1076519849351675883L;
	
	private String ids;
	private Integer labelState;
	private String labelName;
	private Boolean isMaxSerial = false;
	private Integer hotLimit = 10;
	private Integer activityLimit = 10;
	private Integer labelId;
	private Integer valid = Tag.FALSE;
	private Integer worldId;
	private String json;
	private Integer userId;
	
	@Autowired
	private ZTWorldLabelService worldLabelService;
	
	
	/**
	 * 查询标签列表
	 * 
	 * @return
	 */
	public String queryLabel() {
		try {
			worldLabelService.buildLabel(maxSerial, labelState, labelName, sort, order, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询标签列表
	 */
	public String queryLabelForCombobox(){
		PrintWriter out = null;
		try{
			out = response.getWriter();
			List<HTWorldLabel> list = worldLabelService.queryLabelForCombobox();
			JSONArray jsArray = JSONArray.fromObject(list);
			out.print(jsArray.toString());
			out.flush();
		}catch(Exception e) {
			jsonMap.clear();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			JSONObject json = JSONObject.fromObject(jsonMap);
			out.print(json.toString());
			out.flush();
		} finally {
			out.close();
		}
		return null;
	}
	
	/**
	 * 保存标签
	 * 
	 * @return
	 */
	public String saveLabel() {
		try {
			Integer id = worldLabelService.saveLabel(labelName);
			jsonMap.put("labelId", id);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 删除标签
	 * 
	 * @return
	 */
	public String deleteLabels() {
		try {
			worldLabelService.deleteLabel(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新标签序号
	 * 
	 * @return
	 */
	public String updateLabelSerial() {
		String[] ids = request.getParameterValues("reIndexId");
		try {
			worldLabelService.updateLabelSerial(ids);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新热门标签
	 * 
	 * @return
	 */
	public String updateHotLabel() {
		try {
			worldLabelService.updateHotLabel(hotLimit, activityLimit);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询标签织图列表
	 * 
	 * @return
	 */
	public String queryLabelWorld() {
		try {
			worldLabelService.buildLabelWorld(maxId, labelId, valid, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除标签织图
	 * 
	 * @return
	 */
	public String deleteLabelWorld() {
		try {
			worldLabelService.updateLabelWorldValid(labelId, ids, valid);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据织图id查询标签ids
	 * 
	 * @return
	 */
	public String queryLabelIdsWithoutRejectByWIDS() {
		try {
			worldLabelService.buildLabelIdsWithoutReject(worldId, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据json字符串批量修改数据
	 * 
	 * @return
	 */
	public String updateByJSON() {
		try {
			worldLabelService.updateByJSON(json);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新某个织图的标签
	 * @return
	 */
	public String updateWorldLable(){
		try{
			worldLabelService.saveLabelWorld(worldId, userId, labelId,labelName);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public ZTWorldLabelService getWorldLabelService() {
		return worldLabelService;
	}

	public void setWorldLabelService(ZTWorldLabelService worldLabelService) {
		this.worldLabelService = worldLabelService;
	}
	
	public Integer getLabelState() {
		return labelState;
	}

	public void setLabelState(Integer labelState) {
		this.labelState = labelState;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Boolean getIsMaxSerial() {
		return isMaxSerial;
	}

	public void setIsMaxSerial(Boolean isMaxSerial) {
		this.isMaxSerial = isMaxSerial;
	}

	public Integer getHotLimit() {
		return hotLimit;
	}

	public void setHotLimit(Integer hotLimit) {
		this.hotLimit = hotLimit;
	}
	
	public Integer getActivityLimit() {
		return activityLimit;
	}

	public void setActivityLimit(Integer activityLimit) {
		this.activityLimit = activityLimit;
	}

	public Integer getLabelId() {
		return labelId;
	}

	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public Integer getUserId(){
		return this.userId;
	}
	
}
