package com.imzhitu.admin.ztworld.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.ZTWorldType;
import com.imzhitu.admin.common.pojo.ZTWorldTypeLabelDto;


/**
 * <p>
 * 分类信息数据访问接口
 * </p>
 * 
 * 创建时间：2013-8-3
 * @author ztj
 *
 */
public interface HTWorldTypeDao extends BaseDao {
	
	/**
	 * 保存分类
	 * 
	 * @param type
	 */
	public void saveType(ZTWorldType type);
	
	/**
	 * 查询所有分类
	 */
	public List<ZTWorldTypeLabelDto> queryAllType();
	
	/**
	 * 查询分类
	 * @param rowSelection
	 * @return
	 */
	public List<ZTWorldType> queryType(RowSelection rowSelection);
	
	/**
	 * 查询分类
	 * @param maxSerial
	 * @param rowSelection
	 * @return
	 */
	public List<ZTWorldType> queryType(int maxSerial, RowSelection rowSelection);
	
	/**
	 * 查询分类总数
	 * 
	 * @param maxSerial
	 * @return
	 */
	public long queryTypeCount(int maxSerial);
	
	/**
	 * 更新有效性
	 * 
	 * @param ids
	 * @param valid
	 */
	public void updateTypeValid(Integer[] ids, Integer valid);
	
	
}

