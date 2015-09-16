package com.imzhitu.admin.ztworld.dao;

import java.util.LinkedHashMap;
import java.util.List;

import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.HTWorldLabelWorld;
import com.imzhitu.admin.common.pojo.OpActivityWorldValidDto;
import com.imzhitu.admin.common.pojo.ZTWorldLabelWorldDto;

/**
 * <p>
 * 织图-标签数据访问接口
 * </p>
 * 
 * 创建时间：2014-1-17
 * @author ztj
 *
 */
public interface HTWorldLabelWorldDao extends BaseDao {
	
	
	/**
	 * 根据织图id删除
	 * 
	 * @param worldId
	 */
	public void deleteByWorldId(Integer worldId);
	
	/**
	 * 根据标签id删除“织图-标签”关联
	 * @param labelIds
	 */
	public void deleteByLabelIds(Integer[] labelIds);
	
	/**
	 * 保存标签织图关联关系
	 * 
	 * @param labelWorld
	 */
	public void saveLabelWorld(HTWorldLabelWorld labelWorld);
	
	/**
	 * 查询标签id
	 * 
	 * @param worldId
	 * @return
	 */
	public List<Integer> queryLabelIds(Integer worldId);
	
	/**
	 * 查询标签织图
	 * 
	 * @param labelId
	 * @param attrMap
	 * @param rowSelection
	 * @return
	 */
	public List<ZTWorldLabelWorldDto> queryLabelWorld(Integer labelId, 
			LinkedHashMap<String, Object> attrMap,  RowSelection rowSelection);

	/**
	 * 查询标签织图
	 * 
	 * @param maxId
	 * @param labelId
	 * @param attrMap
	 * @param rowSelection
	 * @return
	 */
	public List<ZTWorldLabelWorldDto> queryLabelWorld(Integer maxId, Integer labelId, 
			LinkedHashMap<String, Object> attrMap, RowSelection rowSelection);
	
	/**
	 * 查询标签织图总数
	 * 
	 * @param maxId
	 * @param labelId
	 * @param attrMap
	 * @return
	 */
	public long queryLabelWorldCount(Integer maxId, Integer labelId, LinkedHashMap<String, Object> attrMap);
	
	/**
	 * 查询标签织图总数
	 * 
	 * @param labelId
	 * @param attrMap
	 * @return
	 */
	public long queryLabelWorldCount(Integer labelId,LinkedHashMap<String, Object> attrMap);
	
	/**
	 * 更新标签织图有效性
	 * 
	 * @param worldIds
	 * @param valid
	 */
	public void updateLabelWorldValid(Integer[] worldIds, Integer valid);
	
	/**
	 * 查询最大id 
	 * 
	 * @param labelId
	 * @return
	 */
	public Integer queryMaxId();
	
	/**
	 * 查询标签织图
	 * 
	 * @param worldIds
	 * @param callback
	 */
	public void queryWorldIds(Integer[] worldIds, Integer labelState,
			RowCallback<OpActivityWorldValidDto> callback);
	
	/**
	 * 查询标签id列表
	 * 
	 * @param worldId
	 * @return
	 */
	public List<Integer> queryLabelIdsWithoutReject(Integer worldId);
	
	/**
	 * 查询标签织图
	 * 
	 * @param worldId
	 * @param labelId
	 * @return
	 */
	public HTWorldLabelWorld queryLabelWorld(Integer worldId, Integer labelId);
	
	/**
	 * 更新标签织图有效性
	 * 
	 * @param id
	 * @param valid
	 */
	public void updateLabelWorldValid(Integer id, Integer valid);
	
	/**
	 * 更新标签织图加精
	 * 
	 * @param id
	 * @param superb
	 */
	public void updateLabelWorldSuperb(Integer id, Integer superb);
	
	/**
	 * 更新标签织图序号
	 * 
	 * @param id
	 * @param serial
	 */
	public void updateLabelWorldSerial(Integer id, Integer serial);
	
	/**
	 * 更新htworldhtworld表里面的label
	 * @param worldId
	 * @param labelStr
	 */
	public void updateLabelNameInHtworldHtworld(Integer worldId,String labelStr);
	
}
