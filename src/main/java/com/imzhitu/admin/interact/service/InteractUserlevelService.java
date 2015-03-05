package com.imzhitu.admin.interact.service;

import java.util.Map;
import java.util.List;
import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.UserLevelDto;

public interface InteractUserlevelService extends BaseService{
	/**
	 * 查询用户等级列表
	 */
	public void QueryUserlevelList(int maxId,int start,int limit,Map<String ,Object> jsonMap) throws Exception;
	/**
	 * 查询用户等级列表
	 */
	public List<UserLevelDto> QueryUserLevel() throws Exception;
	
	/**
	 * 根据ids删除用户等级
	 */
	public void DeleteUserlevelByIds(String idsStr)throws Exception;
	
	/**
	 * 根据id查询用户等级
	 */
	public UserLevelDto QueryUserlevelById(Integer id)throws Exception;
	
	/**
	 * 增加用户等级
	 */
	public void AddUserlevel(UserLevelDto userlevelDto)throws Exception;
	
	/**
	 * 更新用户等级
	 */
	public void UpdateUserlevelById(UserLevelDto userlevelDto)throws Exception;
	
	/**
	 * 更新用户等级by RowJson
	 */
	public void updateUserLevelByRowJson(String rowJson)throws Exception;
}
