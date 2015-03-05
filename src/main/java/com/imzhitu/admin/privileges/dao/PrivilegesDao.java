package com.imzhitu.admin.privileges.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.hts.web.common.dao.BaseDao;

/**
 * <p>
 * 权限信息数据访问接口
 * </p>
 * 
 * 创建时间：2013-8-3
 * @author ztj
 *
 */
public interface PrivilegesDao extends BaseDao {

	/**
	 * 根据分组信息删除权限信息
	 * 
	 * @param groupId
	 */
	public void deletePrivilegesByGroupId(Integer groupId);
	
	/**
	 * 查询最后一条记录
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> queryLastPrivilegesMeta();
	
	/**
	 * 根据权限分组id查询该分组下的最大序号
	 * 
	 * @param groupId
	 */
	public Integer queryMaxSerialByGroupId(Integer groupId);
	
	/**
	 * 根据分组id查询权限
	 * 
	 * @param groupId
	 * @return
	 */
	public List<Map<String, Object>> queryPrivilegesByGroupId(Integer groupId);
}
