package com.imzhitu.admin.interact.dao;

import java.util.Date;
import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.InteractWorldLiked;

/**
 * <p>
 * 织图互动，喜欢数据访问接口
 * </p>
 * 
 * 创建时间：2013-11-3
 * @author ztj
 *
 */
@Deprecated
public interface InteractWorldLikedDao extends BaseDao {

	/**
	 * 保存喜欢
	 * 
	 * @param liked
	 */
	public void saveLiked(InteractWorldLiked liked);
	
	/**
	 * 查询喜欢互动
	 * 
	 * @param worldId
	 * @param rowSelection
	 */
	public List<InteractWorldLiked> queryLiked(Integer interactId, RowSelection rowSelection);
	
	/**
	 * 查询喜欢互动
	 * 
	 * @param worldId
	 * @param maxId
	 * @param rowSelection
	 */
	public List<InteractWorldLiked> queryLiked(Integer interactId, int maxId, RowSelection rowSelection);
	
	/**
	 * 查询喜欢互动总数
	 * 
	 * @param worldId
	 */
	public long queryLikedTotal(Integer interactId);
	
	/**
	 * 查询喜欢互动总数
	 * 
	 * @param worldId
	 * @param maxId
	 */
	public long queryLikedTotal(Integer interactId, int maxId);
	
	/**
	 * 查询滞留为完成的喜欢互动
	 * 
	 * @param startDate
	 * @param currentDate
	 * @return
	 */
	public List<InteractWorldLiked> queryUnfinishedLiked(Date startDate, Date currentDate);
	
	/**
	 * 更新完成状态
	 * 
	 * @param id
	 * @param finished
	 */
	public void updateFinished(Integer id, Integer finished);
	
	/**
	 * 根据最大id更新有效状态
	 * 
	 * @param maxInteractId
	 * @param valid
	 */
	public void updateValid(Integer maxInteractId, Integer valid);
	
	/**
	 * 根据互动ids删除喜欢
	 * 
	 * @param interactIds
	 */
	public void deleteByInteractIds(Integer[] interactIds);
	
	/**
	 * 根据互动id删除喜欢
	 * @param interactId
	 */
	public void deleteByInteractId(Integer interactId);
	
	/**
	 * 更新未完成的计划到今天
	 * 
	 * @param now
	 */
	public void updateUnFinishedSchedule(Date now);
	
	/**
	 * 更新计划时间和有效性
	 * @param valid
	 * @param date_schedule
	 */
	public void updateValidAndSCHTimeById(Integer valid,Date date_schedule,Integer id);
	
	/**
	 * 根据interactId查询所有的互动喜欢计划
	 * @param interactId
	 * @return
	 */
	public List<InteractWorldLiked> queryLikedByInteractId(Integer interactId);
}

