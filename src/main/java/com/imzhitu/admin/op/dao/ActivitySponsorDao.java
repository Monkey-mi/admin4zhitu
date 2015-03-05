package com.imzhitu.admin.op.dao;

import java.util.List;

import com.hts.web.base.database.RowCallback;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpActivitySponsor;

/**
 * <p>
 * 活动发起人数据访问接口
 * </p>
 * 
 * 创建时间：2014-3-19
 * @author tianjie
 *
 */
public interface ActivitySponsorDao extends BaseDao {
	
	/**
	 * 保存发起人
	 * 
	 * @param activityId
	 * @param userId
	 */
	public void saveSponsor(Integer activityId, Integer userId);
	
	/**
	 * 根据活动id删除发起人
	 * 
	 * @param activityId
	 */
	public void deleteByActivityId(Integer activityId);
	
	/**
	 * 查询活动发起人
	 * 
	 * @param activityIds
	 * @param callback
	 */
	public void querySponsor(Integer[] activityIds, RowCallback<OpActivitySponsor> callback);
	
	/**
	 * 查询活动发起人
	 * 
	 * @param activityId
	 * @return
	 */
	public List<OpActivitySponsor> querySponsor(Integer activityId);
}
