package com.imzhitu.admin.interact.dao;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.InteractWorldLevelListDto;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface InteractWorldlevelListDao extends BaseDao{
	/**
	 * 查询等级织图列表
	 * @param rowSelection
	 * @return
	 */
	public List<InteractWorldLevelListDto> queryWorldLevelList(Map<String,Object> attr,RowSelection rowSelection);
	
	/**
	 * 根据maxId查询等级织图列表
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<InteractWorldLevelListDto> queryWorldLevelList(Integer maxId,Map<String,Object> attr,RowSelection rowSelection);
	
	/**
	 * 根据maxId查询等级织图总数
	 * @param maxId
	 * @return
	 */
	public long getWorldLevelListCountByMaxId(Integer maxId,Map<String,Object> attr);
	
	/**
	 * 增加等级织图
	 * @param worldId
	 * @param world_level_id
	 * @param validity
	 * @param comment_ids
	 * @param label_ids
	 */
	public void addWorldLevelList(Integer worldId,Integer  world_level_id,Integer validity,Date addDate,Date modifyDate,Integer operatorId);
	
	/**
	 * 根据织图ids删除等级织图
	 * @param wIds
	 */
	public void delWorldLevelListByWIds(Integer[] wIds);
	
	/**
	 * 更新织图等级和有效性
	 * @param worldId
	 * @param world_level_id
	 * @param validity
	 */
	public void updateWorldLevelList(Integer worldId , Integer world_level_id,Integer validity,Date modifyDate,Integer operatorId);
	
	/**
	 * 根据织图id查询该织图是否被设定了等级
	 * @param worldId
	 * @return
	 */
	public boolean checkWorldLevelListIsExistByWId(Integer worldId);
	
	/**
	 * 根据织图id 查询织图等级
	 * @param wid
	 * @return
	 */
	public InteractWorldLevelListDto queryWorldLevelListByWid(Integer wid);
	
}
