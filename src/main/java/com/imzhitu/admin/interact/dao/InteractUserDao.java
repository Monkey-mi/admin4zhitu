package com.imzhitu.admin.interact.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.InteractUser;

/**
 * <p>
 * 用户互动数据访问接口
 * </p>
 * 
 * 创建时间：2014-2-19
 * @author tianjie
 *
 */
public interface InteractUserDao extends BaseDao {

	/**
	 * 保存用户互动
	 * 
	 * @param interact
	 */
	public void saveInteract(InteractUser interact);
	
	/**
	 * 查询用户互动
	 * 
	 * @param rowSelection
	 * @return
	 */
	public List<InteractUser> queryInteract(RowSelection rowSelection);
	
	/**
	 * 查询用户互动
	 * 
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<InteractUser> queryInteract(Integer maxId, RowSelection rowSelection);
	
	/**
	 * 查询用户互动总数
	 * 
	 * @param maxId
	 * @return
	 */
	public long queryInteractCount(Integer maxId);
	
	/**
	 * 根据用户id查询互动
	 * 
	 * @param userId
	 * @return
	 */
	public InteractUser queryUserInteractByUID(Integer userId);
	
	/**
	 * 更新互动
	 * 
	 * @param interactId
	 * @param followCount
	 */
	public void updateInteract(Integer interactId, Integer followCount, Integer duration);
	
	/**
	 * 根据ids删除互动
	 * 
	 * @param ids
	 */
	public void deleteByIds(Integer[] ids);
	
	/**
	 * 根据用户id查询互动
	 * 
	 * @param userId
	 * @return
	 */
	public InteractUser queryInteractByUID(Integer userId);
	
	/**
	 * 根据用户ids查询互动用户id
	 * 
	 * @param userIds
	 * @param callback
	 */
	public void queryUserIdByUIDS(Integer[] userIds, RowCallback<Integer> callback);
	
	/**
	 * 构建用户互动POJO对象
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public InteractUser buildInteract(ResultSet rs) throws SQLException;
	
	
}
