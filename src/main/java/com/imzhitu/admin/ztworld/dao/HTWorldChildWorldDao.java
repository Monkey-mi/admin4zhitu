package com.imzhitu.admin.ztworld.dao;

import java.util.List;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.HTWorldChildWorld;

/**
 * <p>
 * 子世界数据访问对象
 * </p>
 * 
 * 创建时间：2012-11-01
 * 
 * @author ztj
 * 
 */
public interface HTWorldChildWorldDao extends BaseDao {

	/**
	 * 根据织图id删除子世界
	 * 
	 * @param worldIds
	 */
	public void deleteByWorldIds(Integer[] worldIds);
	
	/**
	 * 查询所有子世界信息，同时
	 * @param worldId
	 * @return
	 */
	public List<HTWorldChildWorld> queryChildWorld(Integer worldId);
	
}
