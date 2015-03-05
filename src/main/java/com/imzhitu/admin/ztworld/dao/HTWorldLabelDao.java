package com.imzhitu.admin.ztworld.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.HTWorldLabel;

/**
 * <p>
 * 织图标签数据访问接口
 * </p>
 * 
 * 创建时间:2014-5-7
 * @author tianjie
 *
 */
public interface HTWorldLabelDao extends BaseDao {

	/**
	 * 保存标签
	 * 
	 * @param label
	 */
	public void saveLabel(HTWorldLabel label);
	
	/**
	 * 根据标签属性查询标签列表，默认serial倒序排序
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldLabel> queryLabel(Integer labelState, RowSelection rowSelection);
	
	/**
	 * 查询标签列表
	 * 
	 * @param attrMap
	 * @param orderKey
	 * @param order
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldLabel> queryLabel(LinkedHashMap<String, Object> attrMap, String orderKey, 
			String order, RowSelection rowSelection);
	
	/**
	 * 查询标签列表
	 * 
	 * @param maxSerial
	 * @param attrMap
	 * @param orderKey
	 * @param order
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldLabel> queryLabel(Integer maxSerial, LinkedHashMap<String, Object> attrMap, 
			String orderKey, String order, RowSelection rowSelection);
	
	/**
	 * 查询标签总数
	 * 
	 * @param maxSerial
	 * @param attrMap
	 * @return
	 */
	public long queryLabelCount(Integer maxSerial, LinkedHashMap<String, Object> attrMap);
	
	
	/**
	 * 查询指定分类下的所有标签
	 * 
	 * @param typeId
	 * @return
	 */
	public List<HTWorldLabel> queryAllLabelByTypeId(Integer typeId);
	
	/**
	 * 根据ids删除标签
	 * 
	 * @param ids
	 */
	public void deleteByIds(Integer[] ids);
	
	/**
	 * 更新序号
	 * 
	 * @param id
	 * @param serial
	 */
	public void updateSerial(Integer id, Integer serial);
	
	
	/**
	 * 更新织图数
	 * 
	 * @param id
	 * @param worldCount
	 */
	public void updateWorldCount(Integer id, Integer worldCount);
	
	/**
	 * 更新标签属性
	 * 
	 * @param id
	 * @param state
	 */
	public void updateLabelState(Integer id, Integer state);
	
	/**
	 * 根据名字查询标签
	 * 
	 * @param name
	 * @return
	 */
	public HTWorldLabel queryLabelByName(String name);
	
	/**
	 * 根据ids查询标签Map
	 * 
	 * @param ids
	 * @return
	 */
	public Map<Integer, HTWorldLabel> queryLabelMap(Integer[] ids);
}
