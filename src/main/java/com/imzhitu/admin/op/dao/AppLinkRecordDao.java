package com.imzhitu.admin.op.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpAdAppLinkRecord;

/**
 * <p>
 * App链接点击记录数据访问接口
 * </p>
 * 
 * 创建时间：2013-11-30
 * @author ztj
 *
 */
public interface AppLinkRecordDao extends BaseDao {

	/**
	 * 保存记录
	 * 
	 * @param record
	 */
	public void saveRecord(OpAdAppLinkRecord record);
	
	/**
	 * 查询点击链接
	 * @param appId
	 * @param rowSelection
	 * @return
	 */
	public List<OpAdAppLinkRecord> queryRecord(Integer appId, RowSelection rowSelection);
	
	/**
	 * 查询点击链接
	 * 
	 * @param maxId
	 * @param appId
	 * @param rowSelection
	 * @return
	 */
	public List<OpAdAppLinkRecord> queryRecord(Integer maxId, Integer appId, RowSelection rowSelection);

	/**
	 * 查询点击链接总数
	 * 
	 * @param maxId
	 * @param appId
	 * @return
	 */
	public long queryRecordCount(Integer maxId, Integer appId);
	
}
