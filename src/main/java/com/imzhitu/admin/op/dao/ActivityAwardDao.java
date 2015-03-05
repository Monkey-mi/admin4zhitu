package com.imzhitu.admin.op.dao;

import java.util.List;

import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpActivityAward;

/**
 * <p>
 * 活动奖品数据访问接口
 * </p>
 * 
 * 创建时间：2014-5-19
 * @author tianjie
 *
 */
public interface ActivityAwardDao extends BaseDao {

	/**
	 * 保存奖品
	 * 
	 * @param award
	 */
	public void saveAward(OpActivityAward award);
	
	/**
	 * 更新奖品
	 * 
	 * @param award
	 */
	public void updateAward(OpActivityAward award);
	
	/**
	 * 删除奖品
	 * 
	 * @param ids
	 */
	public void deleteAward(Integer[] ids);
	
	/**
	 * 更新序号
	 * 
	 * @param id
	 * @param serial
	 */
	public void updateSerial(Integer id, Integer serial);
	
	/**
	 * 查询奖品列表
	 * 
	 * @param activityId
	 * @return
	 */
	public List<OpActivityAward> queryAward(Integer activityId);
	
	/**
	 * 查询奖品列表
	 * 
	 * @param activityId
	 * @param rowSelection
	 * @return
	 */
	public List<OpActivityAward> queryAward(Integer activityId, RowSelection rowSelection);
	
	/**
	 * 查询奖品列表
	 * 
	 * @param maxSerial
	 * @param activityId
	 * @param rowSelection
	 * @return
	 */
	public List<OpActivityAward> queryAward(Integer maxSerial, Integer activityId,
			RowSelection rowSelection);
	
	/**
	 * 查询奖品总数
	 * 
	 * @param maxSerial
	 * @param activityId
	 * @return
	 */
	public long queryAwardCount(Integer maxSerial, Integer activityId);
	
	/**
	 * 根据id查询奖品
	 * 
	 * @param id
	 * @return
	 */
	public OpActivityAward queryAwardById(Integer id);
	
	/**
	 * 根据活动ids查询奖品
	 * @param activityId
	 * @param callback
	 */
	public void queryAwards(Integer[] activityIds, RowCallback<OpActivityAward> callback);
}
