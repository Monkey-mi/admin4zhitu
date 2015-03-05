package com.imzhitu.admin.interact.dao;

import java.util.List;
import java.util.Map;

import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.InteractWorld;

/**
 * <p>
 * 织图互动信息数据访问接口
 * </p>
 * 
 * 创建时间：2013-11-4
 * @author ztj
 *
 */
public interface InteractWorldDao extends BaseDao {

	/**
	 * 保存织图互动信息
	 * 
	 * @param interact
	 */
	public void saveInteract(InteractWorld interact);
	
	/**
	 * 根据织图id查询互动信息
	 * 
	 * @param worldId
	 * @return
	 */
	public InteractWorld queryInteractByWorldId(Integer worldId);
	
	/**
	 * 更新互动信息
	 * 
	 * @param id
	 * @param clickCount
	 * @param commentCount
	 * @param likedCount
	 * @param duration
	 */
	public void updateInteract(Integer id, Integer clickCount, Integer commentCount,
			Integer likedCount, Integer duration);
	
	/**
	 * 查询互动列表
	 * 
	 * @param rowSelection
	 * @param attrMap
	 * @return
	 */
	public List<InteractWorld> queryInteract(RowSelection rowSelection, Map<String, Object> attrMap);
	
	/**
	 * 查询互动列表
	 * 
	 * @param maxId
	 * @param rowSelection
	 * @param attrMap
	 * @return
	 */
	public List<InteractWorld> queryInteract(int maxId, RowSelection rowSelection, 
			Map<String, Object> attrMap);
	
	/**
	 * 查询互动总数
	 * 
	 * @param maxId
	 */
	public long queryInteractTotal(int maxId, Map<String, Object> attrMap);
	
	/**
	 * 根据最大id更新有效状态
	 * 
	 * @param maxId
	 * @param valid
	 */
	public void updateValid(Integer maxId, Integer valid);
	
	/**
	 * 删除互动
	 * 
	 * @param ids
	 */
	public void deleteInteract(Integer[] ids);
	
	/**
	 * 删除互动
	 * @param id
	 */
	public void deleteInteract(Integer id);
	
	/**
	 * 根据织图id查询互动id列表
	 * @param worldIds
	 * @return
	 */
	public List<Integer> queryWorldIdByWIDs(Integer[] worldIds);
	
	/**
	 * 根据wids查询wids列表，用于确认查询的id是否处于互动列表中
	 * 
	 * @param wids
	 * @param callback
	 */
	public void queryWorldIdByWIDs(Integer[] wids, RowCallback<Integer> callback);
	
	/**
	 * 根据worldID列表来更新worldID对应的有效状态
	 * @param wids
	 * @param valid
	 */
	public void upInteractValidByWIDs(Integer[] wids,Integer valid);
	
	/**
	 * 根据worldIds列表来查询
	 * @param ids
	 * @return
	 */
	public List<InteractWorld> queryInteractByWIDs(Integer[] ids);
	
	/**
	 * 根据织图id查询互动id
	 */
	public Integer queryIntegerIdByWorldId(Integer wId);
	
}
