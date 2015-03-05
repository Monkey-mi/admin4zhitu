package com.imzhitu.admin.userinfo.dao;

import java.util.List;

import com.hts.web.base.database.RowCallback;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.UserLabel;
import com.imzhitu.admin.common.pojo.UserLabelDto;

/**
 * <p>
 * 用户标签数据访问接口
 * </p>
 * 
 * 创建时间：2014-1-17
 * @author ztj
 *
 */
public interface UserLabelDao extends BaseDao {

	/**
	 * 保存标签
	 * 
	 * @param label
	 */
	public void saveLabel(UserLabel label);
	
	/**
	 * 查询所有标签
	 * 
	 * @param callback
	 */
	public void queryAllLabel(RowCallback<UserLabelDto> callback);
	
	/**
	 * 根据用户id查询标签id列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<Integer> queryLabelIdByUserId(Integer userId);
	
	/**
	 * 删除用户标签
	 * 
	 * @param userId
	 */
	public void deleteLabelByUserId(Integer userId);
	
	/**
	 * 保存
	 * @param labelId
	 * @param userId
	 */
	public void saveLabelUser(Integer labelId, Integer userId);
	
	
}
