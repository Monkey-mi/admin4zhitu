package com.imzhitu.admin.op.dao;

import java.util.List;
import java.util.Map;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpActivity;

/**
 * <p>
 * 广场活动数据访问接口
 * </p>
 * 
 * 创建时间：2013-11-8
 * @author ztj
 *
 */
public interface ActivityDao extends BaseDao {
	
	/**
	 * 保存活动
	 * 
	 * @param activity
	 */
	public void saveActivity(OpActivity activity);
	
	/**
	 * 更新活动
	 * 
	 * @param activity
	 */
	public void updateActivity(OpActivity activity);
	
	/**
	 * 查询活动列表
	 * 
	 * @param rowSelection
	 * @return
	 */
	public List<OpActivity> queryActivity(RowSelection rowSelection);
	
	/**
	 * 查询活动列表
	 * 
	 * @param maxSerial 最大序号
	 * @param rowSelection
	 */
	public List<OpActivity> queryActivity(int maxSerial, RowSelection rowSelection);
	
	/**
	 * 查询活动总数
	 * 
	 * @param maxSerial
	 * @return
	 */
	public long queryActivityTotal(int maxSerial);
	
	
	/**
	 * 根据ids查询活动
	 * 
	 * @param ids
	 * @return
	 */
	public Map<Integer, OpActivity> queryActivityByIds(Integer[] ids);
	
	/**
	 * 根据id查询活动
	 * 
	 * @param id
	 * @return
	 */
	public OpActivity queryActivityById(Integer id);
	
	/**
	 * 根据ids删除活动
	 * 
	 * @param ids
	 */
	public void deleteByIds(Integer[] ids);
	
	/**
	 * 根据id删除活动
	 * 
	 * @param id
	 */
	public void deleteById(Integer id);
	
	
}
