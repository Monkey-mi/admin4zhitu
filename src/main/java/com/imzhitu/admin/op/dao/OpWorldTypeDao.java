package com.imzhitu.admin.op.dao;

import java.util.List;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpWorldType;

/**
 * <p>
 * 广场分类数据访问接口
 * </p>
 * 
 * 创建时间：2014-4-9
 * @author tianjie
 *
 */
public interface OpWorldTypeDao extends BaseDao {
	
	/**
	 * 查询所有有效分类
	 * 
	 * @return
	 */
	public List<OpWorldType> queryAllValidType();

}
