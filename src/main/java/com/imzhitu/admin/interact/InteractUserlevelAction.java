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
import com.imzhitu.admin.interact.service.InteractUserlevelService;
import com.imzhitu.admin.common.pojo.UserLevelDto;


/**
 * @function 用户等级管理控制器
 * @author zxx
 * @time 2014年4月9日 10:38:56
 */
public class InteractUserlevelAction extends BaseCRUDAction{
	private static final long serialVersionUID = -1681882489347047674L;
	private String userLevelIds;	//id列表用逗号隔开
	private Integer id;				//id
	private Integer minFansCount;	//最小喜欢数
	private Integer maxFansCount;	//最大粉丝数
	private Integer minLikedCount;	//最小喜欢数
	private Integer maxLikedCount;	//最大喜欢数
	private Integer minPlayTimes;	//最小播放数
	private Integer maxPlayTimes;	//最大播放数
	private Integer minCommentCount;//最小评论数
	private Integer maxCommentCount;//最大评论数
	private Integer time;			//为期，也就是在这个时间段内完成计划。单位为小时
	private String levelDescription;//等级描述
	private String rowJson;	//跟新数据的json格式
	private Integer weight;	//权重
	
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@Autowired
	private InteractUserlevelService interactUserlevelService;
	
	public void setInteractUserlevelService(InteractUserlevelService interactUserlevelService){
		this.interactUserlevelService = interactUserlevelService;
	}
	public InteractUserlevelService getInteractUserlevelService(){
		return this.interactUserlevelService;
	}
	
	public void setRowJson(String rowJson){
		this.rowJson = rowJson;
	}
	public String getRowJson(){
		return this.rowJson;
	}
	
	public void setUserLevelIds(String userLevelIds){
		this.userLevelIds = userLevelIds;
	}
	
	public String getUserLevelIds(){
		return this.userLevelIds;
	}
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public void setMinFansCount(Integer minFansCount){
		this.minFansCount = minFansCount;
	}
	public Integer getMinFansCount(){
		return this.minFansCount;
	}
	
	public void setMaxFansCount(Integer maxFansCount){
		this.maxFansCount = maxFansCount;
	}
	public Integer getMaxFansCount(){
		return this.maxFansCount;
	}
	
	public void setMinLikedCount(Integer minLikedCount){
		this.minLikedCount = minLikedCount;
	}
	public Integer getMinLikedCount(){
		return this.minLikedCount;
	}
	
	public void setMaxLikedCount(Integer maxLikedCount){
		this.maxLikedCount = maxLikedCount;
	}
	public Integer getMaxLikedCount(){
		return this.maxLikedCount;
	}
	
	public void setMinCommentCount(Integer minCommentCount){
		this.minCommentCount = minCommentCount;
	}
	public Integer getMinCommentCount(){
		return this.minCommentCount;
	}
	
	public void setMaxCommentCount(Integer maxCommentCount){
		this.maxCommentCount = maxCommentCount;
	}
	public Integer getMaxCommentCount(){
		return this.maxCommentCount;
	}
	
	public void setMinPlayTimes(Integer minPlayTimes){
		this.minPlayTimes = minPlayTimes;
	}
	public Integer getMinPlayTimes(){
		return this.minPlayTimes;
	}
	
	public void setMaxPlayTimes(Integer maxPlayTimes){
		this.maxPlayTimes = maxPlayTimes;
	}
	public Integer getMaxPlayTimes(){
		return this.maxPlayTimes;
	}
	
	public void setTime(Integer time){
		this.time = time;
	}
	public Integer getTime(){
		return this.time;
	}
	
	public void setLevelDescription(String levelDescription){
		this.levelDescription = levelDescription;
	}
	public String getLevelDescription(){
		return this.levelDescription;
	}
	
	/**
	 * 查询用户等级列表
	 */
	public String QueryUserlevelList(){
		try{
			interactUserlevelService.QueryUserlevelList(maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String QueryUserLevel(){
		PrintWriter out = null;
		try {
			out = response.getWriter();
			List<UserLevelDto> list = interactUserlevelService.QueryUserLevel();
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
	 * 删除用户等级
	 * 参数：用户等级ids
	 */
	public String DeleteUserlevelByIds(){
		try{
			interactUserlevelService.DeleteUserlevelByIds(userLevelIds);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据id查询用户等级
	 */
	public String QueryUserlevelById(){
		try{
			UserLevelDto userLevelDto = interactUserlevelService.QueryUserlevelById(id);
			JSONUtil.optResult(OptResult.OPT_SUCCESS,userLevelDto,OptResult.JSON_KEY_USER_LEVEL, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 增加用户等级
	 */
	public String AddUserlevel(){
		try{
			interactUserlevelService.AddUserlevel(
					new UserLevelDto(0, minFansCount, maxFansCount, minLikedCount, 
							maxLikedCount, minCommentCount, maxCommentCount, minPlayTimes, 
							maxPlayTimes, time, levelDescription,weight)
					);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新用户等级
	 */
	public String updateUserLevelByRowJson(){
		try{
			interactUserlevelService.updateUserLevelByRowJson(rowJson);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optSuccess(OptResult.UPDATE_FAILED,jsonMap);
			e.printStackTrace();
		}
		return StrutsKey.JSON;
	}
}
