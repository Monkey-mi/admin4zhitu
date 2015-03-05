package com.imzhitu.admin.userinfo.dao;

import java.util.List;
import java.util.Map;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.UserVerify;

/**
 * <p>
 * 用户认证信息数据访问接口
 * </p>
 * 
 * 创建时间：2014-7-16
 * @author tianjie
 *
 */
public interface UserVerifyDao extends BaseDao {

	/**
	 * 添加认证类型
	 * 
	 * @param verify
	 */
	public void saveVerify(UserVerify verify);
	
	/**
	 * 查询认证列表
	 * 
	 * @param rowSelection
	 * @return
	 */
	public List<UserVerify> queryVerify(RowSelection rowSelection);
	
	/**
	 * 查询所有认证信息
	 * 
	 * @return
	 */
	public Map<Integer, UserVerify> queryAllVerifyMap();
	
	/**
	 * 查询认证列表
	 * 
	 * @param maxSerial
	 * @param rowSelection
	 * @return
	 */
	public List<UserVerify> queryVerify(Integer maxSerial, RowSelection rowSelection);
	
	/**
	 * 查询认证信息
	 * 
	 * @param id
	 * @return
	 */
	public UserVerify queryVerify(Integer id);
	
	
	/**
	 * 查询认证信息总数
	 * 
	 * @param maxSerial
	 * @return
	 */
	public long queryVerifyCount(Integer maxSerial);
	
	/**
	 * 更新认证
	 * 
	 * @param verify
	 */
	public void updateVerify(UserVerify verify);
	
	/**
	 * 删除认证
	 * 
	 * @param id
	 */
	public void deleteVerify(Integer[] ids);
	
	/**
	 * 更新认证排序
	 * 
	 * @param id
	 * @param serial
	 */
	public void updateVerifySerial(Integer id, Integer serial);
	
}
