package com.imzhitu.admin.ztworld.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.HTWorldTypeWorld;
import com.imzhitu.admin.common.pojo.ZTWorldTypeWorldDto;

/**
 * <p>
 * 分类织图数据访问接口
 * </p>
 * 
 * 创建时间：2014-4-9
 * @author tianjie
 *
 */
public interface HTWorldTypeWorldDao extends BaseDao {

	
	/**
	 * 保存织图-分类关系
	 * @param typeWorld
	 */
	public void saveTypeWorld(HTWorldTypeWorld typeWorld);
	
	/**
	 * 更新分类织图列表
	 * 
	 * @param maxId
	 * @param label
	 * @return
	 */
	public List<ZTWorldTypeWorldDto> queryTypeWorld(String sort, String order, Map<String, Object> attrMap, RowSelection rowSelection);
	
	/**
	 * 查询分类织图列表
	 * 
	 * @param maxId
	 * @param sort
	 * @param order
	 * @param attrMap
	 * @param rowSelection
	 * @return
	 */
	public List<ZTWorldTypeWorldDto> queryTypeWorld(int maxId, String sort, String order, Map<String, Object> attrMap, RowSelection rowSelection);
	
	
	/**
	 * 查询分类织图总数
	 * 
	 * @param maxId
	 * @param attrMap
	 * @return
	 */
	public long queryTypeWorldCountByMaxId(int maxId, Map<String, Object> attrMap);
	
	
	/**
	 * 查询推荐总数
	 * 
	 * @return
	 */
	public long queryTypeWorldCount();
	
	/**
	 * 根据织图id删除推荐
	 * 
	 * @param worldId
	 */
	public void deleteByWorldId(Integer worldId);
	
	/**
	 * 删除推荐
	 * 
	 * @param id
	 */
	public void deleteTypeWorld(Integer id);
	
	/**
	 * 批量删除推荐
	 * 
	 * @param idStr
	 */
	public void deleteTypeWorld(Integer[] ids);

	/**
	 * 查询推荐织图id
	 * 
	 * @param ids
	 * @return
	 */
	public List<Integer> queryRecommendWorldIdsByTypeWorldIds(Integer[] ids);
	
	/**
	 * 更新分类
	 * 
	 * @param id
	 * @param typeId
	 */
	public void updateType(int id, int typeId);
	
	/**
	 * 更新精品标记
	 * 
	 * @param id
	 * @param superb
	 */
	public void updateSuperb(int id, int superb);
	
	/**
	 * 更新权重
	 * 
	 * @param id
	 * @param weight
	 */
	public void updateWeight(int id, int weight);
	
	/**
	 * 更新有效性
	 * 
	 * @param worldId
	 * @param valid
	 */
	public int updateValidByWorldId(Integer worldId, Integer valid);
	
	/**
	 * 更新序号
	 * 
	 * @param worldId
	 * @param serial
	 */
	public void updateSerial(Integer worldId, Integer serial);
	
	/**
	 * 更新推荐id
	 * 
	 * @param worldId
	 * @param recommenderId
	 */
	public void updateRecommenderId(Integer worldId, Integer recommenderId);
	
	/**
	 * 更新分类id
	 * 
	 * @param worldId
	 * @param typeId
	 */
	public void updateTypeId(Integer worldId, Integer typeId);
	
	/**
	 * 更新分类织图有效性
	 * 
	 * @param ids
	 */
	public void updateTypeWorldValid(Integer[] ids);
	
	/**
	 * 更新所有推荐分类织图有效性
	 * 
	 * @param ids
	 */
	public void updateAllRecommendTypeWorldValid(Integer valid);
	
	/**
	 * 根据有效性查询分类织图
	 * 
	 * @param valid
	 * @return
	 */
	public List<ZTWorldTypeWorldDto> queryRecommendTypeWorldByValid(Integer valid);
	
	/**
	 * 根据worldds查询分类织图
	 * 
	 * @param ids
	 * @return
	 */
	public List<ZTWorldTypeWorldDto> queryTypeWorldByIds(Integer[] worldIds);
	
	/**
	 * 批量更新排序情况
	 * @param ids
	 */
	public void updateIsSorted(Integer[] ids,Integer isSorted);
	
	/**
	 * 根据worldIds更新有效性
	 * @param worldIds
	 */
	public void updateTypeWorldValidByWorldIds(Integer[] worldIds);
	
	/**
	 * 根据worldId更新精品标志
	 */
	public void updateSuperbByWId(Integer worldId,int superb);
	
	/**
	 * 根据wids来查询分类织图
	 * @param wids
	 * @return
	 */
	public List<ZTWorldTypeWorldDto> queryTypeWorldByWids(Integer[] wids);
	
	/**
	 * 根据wid来更新最后修改时间
	 * @param wid
	 * @param modifyDate
	 */
	public void updateModifyDateByWid(Integer wid,Date modifyDate);
	
	/**
	 * 修改精选织图点评
	 */
	public void updateTypeWorldReview(Integer worldId,String review);
	
	/**
	 * 根据userId查询分类织图总数
	 * @param userId
	 * @return
	 */
	public long queryTypeWorldCountByUserId(Integer userId);
}
