package com.imzhitu.admin.op.dao;

import java.util.LinkedHashMap;
import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpActivityWinner;
import com.imzhitu.admin.common.pojo.OpActivityWorldDto;

/**
 * <p>
 * 活动获胜织图数据访问对象
 * </p>
 * 
 * 创建时间：2014-4-9
 * @author tianjie
 *
 */
public interface ActivityWinnerDao extends BaseDao {
	
	/**
	 * 查询活动织图列表
	 * 
	 * @param activityId
	 * @param attrMap
	 * @param rowSelection
	 * 
	 * @return
	 */
	public List<OpActivityWorldDto> queryWinner(Integer activityId, 
			LinkedHashMap<String, Object> attrMap, RowSelection rowSelection);
	
	/**
	 * 查询活动织图列表
	 * 
	 * @param maxId
	 * @param activityId
	 * @param attrMap
	 * @param rowSelection
	 * 
	 * @return
	 */
	public List<OpActivityWorldDto> queryWinner(Integer maxId, Integer activityId, 
			LinkedHashMap<String, Object> attrMap, RowSelection rowSelection);
	
	/**
	 * 查询活动织图总数
	 * 
	 * @param maxId
	 * @param activityId
	 * @param attrMap
	 * 
	 * @return
	 */
	public long queryWinnerCount(Integer maxId,
			Integer activityId, LinkedHashMap<String, Object> attrMap);
	
	/**
	 * 保存获胜织图
	 * 
	 * @param activityId
	 * @param worldId
	 * @param weight
	 */
	public void saveWinner(OpActivityWinner winner);
	
	/**
	 * 删除获胜织图
	 * 
	 * @param activityId
	 * @param worldId
	 */
	public void deleteWinner(Integer activityId, Integer worldId);
	
	/**
	 * 查询奖品id
	 * 
	 * @param activityId
	 * @param worldId
	 * @return
	 */
	public Integer queryAwardId(Integer activityId, Integer worldId);
	
	/**
	 * 更行奖品id
	 * 
	 * @param activityId
	 * @param worldId
	 * @param awardId
	 */
	public void updateAwardId(Integer activityId, Integer worldId, Integer awardId);
	

	
	
}
