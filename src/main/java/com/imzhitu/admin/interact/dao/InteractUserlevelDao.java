package com.imzhitu.admin.interact.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.UserLevelDto;

public interface InteractUserlevelDao extends BaseDao{
	/**
	 * 查询用户等级列表
	 */
	public List<UserLevelDto> QueryUserlevelList(RowSelection rowSelection);
	
	/**
	 * 查询用户等级列表
	 */
	public List<UserLevelDto> QueryUserlevelList();
	
	/**
	 * 根据id查询用户等级
	 */
	public UserLevelDto QueryUserlevelById(Integer id)throws Exception;
	
	/**
	 * 根据ids删除用户等级
	 */
	public void DeleteUserlevelByIds(Integer[] ids)throws Exception;
	
	/**
	 * 增加用户等级
	 */
	public void AddUserlevel(UserLevelDto userlevelDto)throws Exception;
	
	/**
	 * 根据id来更新用户等级
	 */
	public void UpdateUserlevelById(UserLevelDto userlevelDto)throws Exception;
	/**
	 * 查询用户等级总数
	 */
	public long GetUserLevelCountByMaxId(int maxId);
	
	/**
	 * 根据maxId查询用户等级
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<UserLevelDto> QueryUserlevelListByMaxId(int maxId,RowSelection rowSelection);
	
	/**
	 * 查询用户等级列表
	 * @return
	 */
	public List<UserLevelDto> QueryUserLevel();

}
