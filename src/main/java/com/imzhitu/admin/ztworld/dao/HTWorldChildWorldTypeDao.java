package com.imzhitu.admin.ztworld.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.HTWorldChildWorldType;
import com.hts.web.common.pojo.HTWorldChildWorldTypeDto;

/**
 * <p>
 * 子世界类型数据访问接口
 * </p>
 * 
 * 创建时间：2014-6-13
 * @author tianjie
 *
 */
public interface HTWorldChildWorldTypeDao extends BaseDao {

	/**
	 * 保存类型
	 * 
	 * @param type
	 */
	public void saveType(HTWorldChildWorldType type);
	
	/**
	 * 更新类型
	 * 
	 * @param type
	 */
	public void updateType(HTWorldChildWorldType type);
	
	/**
	 * 查询类型列表
	 * 
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldChildWorldType> queryType(RowSelection rowSelection);
	
	/**
	 * 查询类型列表
	 * 
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldChildWorldTypeDto> queryTypeDto(RowSelection rowSelection);
	
	/**
	 * 查询类型列表
	 * 
	 * @param maxSerial
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldChildWorldTypeDto> queryTypeDto(int maxSerial, RowSelection rowSelection);
	
	/**
	 * 查询类型总数
	 * 
	 * @param maxSerial
	 * @return
	 */
	public long queryTypeCount(int maxSerial);
	
	/**
	 * 删除子世界类型
	 * 
	 * @param ids
	 */
	public void deleteType(Integer[] ids);
	
	/**
	 * 更新序号
	 * 
	 * @param id
	 * @param serial
	 */
	public void updateSerial(Integer id, Integer serial);
	
	/**
	 * 根据id查询子世界类型
	 * 
	 * @param id
	 * @return
	 */
	public HTWorldChildWorldTypeDto queryTypeById(Integer id);
}
