package com.imzhitu.admin.privileges.dao;

import com.hts.web.common.dao.BaseDao;

/**
 * <p>
 * 权限分组数据访问接口
 * </p>
 * 
 * 创建时间：2013-8-3
 * @author ztj
 *
 */
public interface PrivilegesGroupDao extends BaseDao {

	/**
	 * 查询最大权限分组序号
	 * @return
	 */
	public Integer queryMaxSerial();

	/**
	 * 根据id删除角色权限分组信息
	 * 
	 * @param groupId
	 */
	public void deleteRolePrivilegesGroupByGroupId(Integer groupId);
}
