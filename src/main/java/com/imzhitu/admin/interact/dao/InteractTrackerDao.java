package com.imzhitu.admin.interact.dao;

import java.util.Date;
import java.util.List;

import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.InteractTracker;

/**
 * <p>
 * 互动跟踪数据访问接口
 * </p>
 * 
 * 创建时间：2014-2-27
 * @author tianjie
 *
 */
public interface InteractTrackerDao extends BaseDao {

	/**
	 * 保存互动跟踪
	 * 
	 * @param tracker
	 */
	public void saveTrack(InteractTracker tracker);
	
	/**
	 * 更新最后互动时间
	 * 
	 * @param id
	 * @param lastDate
	 */
	public void updateLastInteractDate(Integer id, Date lastDate);
	
	/**
	 * 更新最后跟踪时间
	 * 
	 * @param lastDate
	 */
	public void updateLastTrackDate(Date lastDate);
	
	/**
	 * 更新有效性
	 * 
	 * @param valid
	 */
	public void updateTrackValid(Integer valid);
	
	/**
	 * 查询所有跟踪器
	 * 
	 * @return
	 */
	public List<InteractTracker> queryTracker();
	
}
