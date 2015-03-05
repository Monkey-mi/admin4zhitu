package com.imzhitu.admin.op.dao;

import com.hts.web.common.dao.BaseDao;

/**
 * <p>
 * 系统私信数据访问对象
 * </p>
 * 
 * 创建时间：2013-12-2
 * @author ztj
 *
 */
public interface SysMsgDao extends BaseDao {
	
	/**
	 * 保存私信
	 * @param msg
	 */
//	public void saveMsg(OpSysMsg msg);	
	
	/**
	 * 根据id删除私信
	 * 
	 * @param id
	 */
	public void deleteMsgById(Integer id);
	
}
