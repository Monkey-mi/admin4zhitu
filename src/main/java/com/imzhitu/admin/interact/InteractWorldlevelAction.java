package com.imzhitu.admin.interact;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.interact.service.InteractWorldlevelService;
import com.imzhitu.admin.common.pojo.ZTWorldLevelDto;

/**
 * 织图等级管理
 * @author zxx
 *
 */
public class InteractWorldlevelAction extends BaseCRUDAction{
	
	private static final long serialVersionUID = -5385536898420838984L;
	
	private String ids;//等级ids
	private Integer id;//等级id
	private Integer min_fans_count;
	private Integer max_fans_count;
	private Integer min_liked_count;
	private Integer max_liked_count;
	private Integer min_comment_count;
	private Integer max_comment_count;
	private Integer min_play_times;
	private Integer max_play_times;
	private Integer time;
	private String level_description;
	private Integer world_id;//织图id
	private Integer label_id;//标签id
	private String labelsStr;
	private String comments;
	private String rowJson;
	private Integer weight;//权重
	
	@Autowired
	private InteractWorldlevelService interactWorldlevelService;
	
	
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public void setRowJson(String rowJson){
		this.rowJson = rowJson;
	}
	public String getRowJson(){
		return this.rowJson;
	}
	
	public void setMin_fans_count(Integer min_fans_count){
		this.min_fans_count = min_fans_count;
	}
	public Integer getMin_fans_count(){
		return this.min_fans_count;
	}
	
	public void setMax_fans_count(Integer max_fans_count){
		this.max_fans_count = max_fans_count;
	}
	public Integer getMax_fans_count(){
		return this.max_fans_count;
	}
	public void setComments(String comments){
		this.comments =comments;
	}
	public String getComments(){
		return this.comments;
	}
	public void setLabelsStr(String labelsStr){
		this.labelsStr = labelsStr;
	}
	public String getLabelsStr(){
		return this.labelsStr;
	}
	public void setWorld_id(Integer world_id){
		this.world_id = world_id;
	}
	public Integer getWorld_id(){
		return this.world_id;
	}
	public void setLabel_id(Integer label_id){
		this.label_id =label_id;
	}
	public Integer getLabel_id(){
		return this.label_id;
	}
	public void setIds(String ids){
		this.ids = ids;
	}
	public String getIds(){
		return this.ids;
	}
	public void setInteractWorldlevelService(InteractWorldlevelService interactWorldlevelService){
		this.interactWorldlevelService = interactWorldlevelService;
	}
	public InteractWorldlevelService getInteractWorldlevelService(){
		return this.interactWorldlevelService;
	}
	
	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getId(){
		return this.id;
	}
	
	public void setMin_liked_count(Integer min_liked_count){
		this.min_liked_count = min_liked_count;
	}
	public Integer getMin_liked_count(){
		return this.min_liked_count;
	}
	
	public void setMax_liked_count(Integer max_liked_count){
		this.max_liked_count = max_liked_count;
	}
	public Integer getMax_liked_count(){
		return this.max_liked_count;
	}
	
	public void setMin_comment_count(Integer min_comment_count){
		this.min_comment_count = min_comment_count;
	}
	public Integer getMin_comment_count(){
		return this.min_comment_count;
	}
	
	public void setMax_comment_count(Integer max_comment_count){
		this.max_comment_count = max_comment_count;
	}
	public Integer getMax_comment_count(){
		return this.max_comment_count;
	}
	
	public void setMin_play_times(Integer min_play_times){
		this.min_play_times = min_play_times;
	}
	public Integer getMin_play_times(){
		return this.min_play_times;
	}
	
	public void setMax_play_times(Integer max_play_times){
		this.max_play_times = max_play_times;
	}
	public Integer getMax_play_times(){
		return this.max_play_times;
	}
	
	public void setTime(Integer time){
		this.time = time;
	}
	public Integer getTime(){
		return this.time;
	}
	
	public void setLevel_description(String level_description){
		this.level_description = level_description;
	}
	public String getLevel_description(){
		return this.level_description;
	}
	
	/**
	 * 查询织图等级列表
	 * @return
	 */
	public String QueryWorldlevelList(){
		try{
			interactWorldlevelService.QueryWorldlevelList(maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			e.printStackTrace();
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询织图等级列表
	 */
	public String QueryoWorldLevel(){
		PrintWriter out = null;
		try {
			out = response.getWriter();
			List<ZTWorldLevelDto> list = interactWorldlevelService.QueryWorldLevel();
			JSONArray jsArray = JSONArray.fromObject(list);
			out.print(jsArray.toString());
			out.flush();
		} catch(Exception e) {
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
	 * 根据ids删除织图等级
	 * @return
	 */
	public String DeleteWorldlevelByIds(){
		try{
			interactWorldlevelService.DeleteWorldlevelByIds(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS,jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optSuccess(OptResult.DELETE_FAILED,jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 添加织图等级
	 * @return
	 */
	public String AddWorldlevel(){
		try{
			interactWorldlevelService.AddWorldlevel(new ZTWorldLevelDto(0,
					min_fans_count,max_fans_count,
					min_liked_count,max_liked_count,
					min_comment_count,max_comment_count,
					min_play_times,max_play_times,
					time,level_description,weight));
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			e.printStackTrace();			
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 添加等级织图
	 */
	public String AddLevelWorld(){
		try{
			interactWorldlevelService.AddLevelWorld(world_id,id,null,comments);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			e.printStackTrace();
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新
	 */
	public String updateWorldLevelByRowJson(){
		try{
			interactWorldlevelService.updateWorldLevelByRowJson(rowJson);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optSuccess(OptResult.UPDATE_FAILED,jsonMap);
			e.printStackTrace();
		}
		return StrutsKey.JSON;
	}
}
