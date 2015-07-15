package com.imzhitu.admin.interact.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.InteractUserFollow;

/**
 * <p>
 * 用户关注互动数据访问接口
 * </p>
 * 
 * 创建时间：2014-2-19
 * @author tianjie
 *
 */
@Deprecated
public interface InteractUserFollowDao extends BaseDao {

	/**
	 * 保存粉丝互动
	 * 
	 * @param concern
	 */
	public void saveFollow(InteractUserFollow follow);

	/**
	 * 查询粉丝互动
	 * 
	 * @param interactId
	 * @return
	 */
	public List<InteractUserFollow> queryFollow(Integer interactId, RowSelection rowSelection);
	
	/**
	 * 查询粉丝互动
	 * 
	 * @param interactId
	 * @param maxId
	 * @return
	 */
	public List<InteractUserFollow> queryFollow(Integer interactId, Integer maxId,
			RowSelection rowSelection);
	
	/**
	 * 查询粉丝互动总数
	 * 
	 * @param interactId
	 * @param maxId
	 * @return
	 */
	public long queryFollowCount(Integer interactId, Integer maxId);
	
	/**
	 * 根据互动id删除粉丝
	 * 
	 * @param interactId
	 */
	public void deleteByInteractId(Integer[] interactId);
	
	/**
	 * 查询未完成粉丝
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<InteractUserFollow> queryUnFinishedFollow(Date startDate, Date endDate);
	
	
	/**
	 * 更新未完成计划时间
	 * 
	 * @param now
	 */
	public void updateUnFinishedSchedule(Date now);
	
	/**
	 * 更新未完成状态
	 * 
	 * @param id
	 * @param finished
	 */
	public void updateFinished(Integer id, Integer finished);
	
	
	
	/**
	 * 构建UserInteractFollowPOJO
	 * 
	 * @param rs
	 * @return
	 */
	public InteractUserFollow buildFollow(ResultSet rs) throws SQLException;
	
}
