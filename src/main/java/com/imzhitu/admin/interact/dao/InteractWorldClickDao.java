package com.imzhitu.admin.interact.dao;

import java.util.Date;
import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.InteractWorldClick;

@Deprecated
public interface InteractWorldClickDao extends BaseDao {

	/**
	 * 保存播放
	 * 
	 * @param click
	 */
	public void saveClick(InteractWorldClick click);
	
	/**
	 * 查询点击互动
	 * 
	 * @param worldId
	 * @param rowSelection
	 */
	public List<InteractWorldClick> queryClick(Integer interactId, RowSelection rowSelection);
	
	/**
	 * 查询点击互动
	 * 
	 * @param interactId
	 * @param maxId
	 * @param rowSelection
	 */
	public List<InteractWorldClick> queryClick(Integer interactId, int maxId, RowSelection rowSelection);
	
	/**
	 * 查询点击互动总数
	 * 
	 * @param interactId
	 */
	public long queryClickTotal(Integer interactId);
	
	/**
	 * 查询点击互动总数
	 * 
	 * @param interactId
	 * @param maxId
	 */
	public long queryClickTotal(Integer interactId, int maxId);
	
	/**
	 * 查询滞后的播放互动任务
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<InteractWorldClick> queryUnFinishedClick(Date startDate, Date endDate);
	
	/**
	 * 更新完成状态
	 * 
	 * @param id
	 * @param finished
	 */
	public void updateFinished(Integer id, Integer finished);
	
	/**
	 * 根据最大互动id更新有效状态
	 * 
	 * @param maxInteractId
	 * @param valid
	 */
	public void updateValid(Integer maxInteractId, Integer valid);
	
	/**
	 * 根据互动ids删除播放
	 * 
	 * @param interactIds
	 */
	public void deleteByInteractIds(Integer[] interactIds);
	
	/**
	 * 根据互动id删除播放
	 * 
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
	 * 根据interactId查询所有的播放计划
	 * @param interactId
	 * @return
	 */
	public List<InteractWorldClick> queryClickbyInteractId(Integer interactId);
}
