package com.imzhitu.admin.interact.dao;

import java.util.Date;
import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.InteractWorldComment;
import com.imzhitu.admin.common.pojo.InteractWorldCommentDto;

/**
 * <p>
 * 织图互动，评论数据访问接口
 * </p>
 * 
 * 创建时间：2013-11-3
 * @author ztj
 *
 */
@Deprecated
public interface InteractWorldCommentDao extends BaseDao {
	
	/**
	 * 保存评论
	 * 
	 * @param comment
	 */
	public void saveComment(InteractWorldComment comment);
	
	
	/**
	 * 查询评论互动
	 * 
	 * @param worldId
	 * @param rowSelection
	 */
	public List<InteractWorldCommentDto> queryComment(Integer interactId, RowSelection rowSelection);
	
	/**
	 * 查询评论互动
	 * 
	 * @param worldId
	 * @param maxId
	 * @param rowSelection
	 */
	public List<InteractWorldCommentDto> queryComment(Integer interactId, int maxId, RowSelection rowSelection);
	
	/**
	 * 查询评论互动总数
	 * 
	 * @param worldId
	 */
	public long queryCommentTotal(Integer interactId);
	
	/**
	 * 查询评论互动总数
	 * 
	 * @param worldId
	 * @param maxId
	 */
	public long queryCommentTotal(Integer interactId, int maxId);
	
	/**
	 * 查询滞后的评论互动
	 * 
	 * @param currentDate
	 * @return
	 */
	public List<InteractWorldCommentDto> queryUnFinishedComment(Date startDate, Date currentDate);
	
	/**
	 * 更新完成状态
	 * 
	 * @param id
	 * @param finished
	 */
	public void updateFinished(Integer id, Integer finished);
	
	/**
	 * 根据最大互动id更新评论有效状态
	 * 
	 * @param maxInteractId
	 * @param valid
	 */
	public void updateValid(Integer maxInteractId, Integer valid);
	
	/**
	 * 根据互动ids删除评论
	 * 
	 * @param interactIds
	 */
	public void deleteByInteractIds(Integer[] interactIds);
	
	/**
	 * 根据互动id删除评论
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
	 * 根据interactId查询所有的评论互动计划
	 * @param interactId
	 * @return
	 */
	public List<InteractWorldCommentDto> queryCommentByInteractId(Integer interactId);
	
	/**
	 * 删除评论计划by ids
	 */
	public void deleteInteractCommentByids(Integer[] ids);
	
	/**
	 * 根据id来更新comment_id
	 * @param id
	 * @param commentId
	 */
	public void updateCommentIdById(Integer id,Integer commentId);
}
