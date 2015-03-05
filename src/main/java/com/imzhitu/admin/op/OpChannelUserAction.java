package com.imzhitu.admin.op;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.AdminUserDetails;
import com.imzhitu.admin.common.pojo.OpChannelUserDto;
import com.imzhitu.admin.op.service.OpChannelUserService;

public class OpChannelUserAction extends BaseCRUDAction{
	private static final long serialVersionUID = -8148145704113830048L;
	
	@Autowired
	private OpChannelUserService service;
	
	private Integer id;			//id
	private Integer userId;		//用户id
	private Integer valid;		//有效性
	private Integer channelId;	//频道id
	private String ids;			//id 字符串
	private String userIds;		//用户ids
	
	private Integer verifyId;	//认证id
	private Integer worldId;	//织图id
	
	
	public Integer getWorldId() {
		return worldId;
	}
	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}
	public Integer getVerifyId() {
		return verifyId;
	}
	public void setVerifyId(Integer verifyId) {
		this.verifyId = verifyId;
	}
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public OpChannelUserService getService() {
		return service;
	}
	public void setService(OpChannelUserService service) {
		this.service = service;
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
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	
	public String queryChannelUserForList(){
		try{
			OpChannelUserDto dto = new OpChannelUserDto();
			dto.setChannelId(channelId);
			service.queryChannelUserForList(dto,maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String updateChannelUserValid(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			service.updateChannelUserValid(id, userId, channelId, valid, user.getId());
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(OptResult.UPDATE_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String insertChannelUser(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			service.insertChannelUser(userId, channelId, Tag.TRUE, user.getId());
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(OptResult.ADD_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String addChannelUserByVerifyId(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			service.addChannelUserByVerifyId(userId, verifyId, Tag.TRUE, user.getId());
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(OptResult.ADD_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String addChannelUserByWorldId(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			service.addChannelUserByWorldId(worldId, channelId, Tag.TRUE, user.getId());
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(OptResult.ADD_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String batchInsertChannelUser(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			service.batchInsertChannelUser(userIds, channelId, user.getId());
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(OptResult.ADD_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String deleteChannelUser(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			service.deleteChannelUser(id, userId, channelId, valid, user.getId());
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(OptResult.DELETE_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String deleteChannelUserByIds(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Integer[] uid = StringUtil.convertStringToIds(userIds);
			for(int i=0;i<uid.length; i++){
				service.deleteChannelUser(id, uid[i], channelId, valid, user.getId());
			}
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(OptResult.DELETE_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}

}
