package com.imzhitu.admin.interact;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.interact.service.InteractUserlevelListService;
import com.imzhitu.admin.common.pojo.AdminUserDetails;
import com.imzhitu.admin.common.pojo.UserLevelListDto;

/**
 * 用户等级列表管理
 * @author zxx
 * @time 2014年4月17日 16:40:29
 */
public class InteractUserlevelListAction extends BaseCRUDAction{
	
	private static final long serialVersionUID = 6236819779272356421L;
	
	private String ids;
	private Integer id;
	private Integer userId;
	private Integer userLevelId;
	private Integer validity;
	private Integer timeType;//时间类型，0计划时间，1，添加时间，2修改时间.用来代表beginTime、和endTime的类型
	private Date beginTime;
	private Date endTime;
	
	@Autowired
	private InteractUserlevelListService interactUserlevelListService;
	
	public void setTimeType(Integer timeType){
		this.timeType = timeType;
	}
	public Integer getTimeType(){
		return this.timeType;
	}
	
	public void setBeginTime(Date beginTime){
		this.beginTime = beginTime;
	}
	public Date getBeginTime(){
		return this.beginTime;
	}
	
	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}
	public Date getEndTime(){
		return this.endTime;
	}
	
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getId(){
		return this.id;
	}
	public void setUserId(Integer userId){
		this.userId =userId;
	}
	public Integer getUserId(){
		return this.userId;
	}
	public void setUserLevelId(Integer userLevelId){
		this.userLevelId = userLevelId;
	}
	public Integer getUserLevelId(){
		return this.userLevelId;
	}
	public void setValidity(Integer validity){
		this.validity = validity;
	}
	public Integer getValidity(){
		return this.validity;
	}
	public void setIds(String ids){
		this.ids = ids;
	}
	public String getIds(){
		return this.ids;
	}
	public void setInteractUserlevelListService(InteractUserlevelListService interactUserlevelListService){
		this.interactUserlevelListService = interactUserlevelListService;
	}
	public InteractUserlevelListService getInteractUserlevelListService(){
		return this.interactUserlevelListService;
	}
	
	public String QueryUserlevelList(){
		try{
			interactUserlevelListService.QueryUserlevelList(maxId,timeType,beginTime,endTime,userId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			e.printStackTrace();
		}
		return StrutsKey.JSON;
	}
	
	public String DeleteUserlevelByIds(){
		try{
			interactUserlevelListService.DeleteUserlevelByIds(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			e.printStackTrace();
		}
		return StrutsKey.JSON;
		
	}
	
	/**
	 * 增加等级用户
	 * @return
	 */
	public String AddUserlevel(){
		try{
			Date now = new Date();
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			interactUserlevelListService.AddUserlevel(new UserLevelListDto(0,userId,userLevelId,Tag.TRUE, null,null,now,now,user.getId(),null));
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			e.printStackTrace();
		}
		return StrutsKey.JSON;
	}

	/**
	 * 根据用户id查询用户等级
	 */
	public String QueryUserlevelByUserId(){
		try{
			UserLevelListDto userlevel = interactUserlevelListService.QueryUserlevelByUserId(userId);
			JSONUtil.optResult(OptResult.OPT_SUCCESS,userlevel,OptResult.JSON_KEY_USER_LEVEL, jsonMap);
		}catch(Exception e){
			e.printStackTrace();
		}
		return StrutsKey.JSON;
	}
}
