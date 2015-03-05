package com.imzhitu.admin.userinfo.dao;

import java.util.LinkedHashMap;
import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.UserMsgRecipientDto;

/**
 * <p>
 * 私信数据访问接口
 * </p>
 * 
 * 创建时间：2014-5-8
 * @author tianjie
 *
 */
public interface UserMsgDao extends BaseDao {
	
	/**
	 * 查询发送者索引列表
	 * 
	 * @param recipientId
	 * @param attrMap
	 * @param rowSelection
	 * @return
	 */
	public List<UserMsgRecipientDto> queryRecipientMsgBox(Integer recipientId, 
			LinkedHashMap<String, Object> attrMap, RowSelection rowSelection);
	
	
	/**
	 * 查询发送者索引列表
	 * 
	 * @param maxId
	 * @param recipientId
	 * @param attrMap
	 * @param rowSelection
	 * @return
	 */
	public List<UserMsgRecipientDto> queryRecipientMsgBox(Integer maxId, Integer recipientId, 
			LinkedHashMap<String, Object> attrMap, RowSelection rowSelection);
	
	
	/**
	 * 查询发送者索引总数
	 * 
	 * @param maxId
	 * @param recipientId
	 * @param attrMap
	 * @return
	 */
	public long queryRecipientMsgBoxCount(Integer maxId, Integer recipientId, 
			LinkedHashMap<String, Object> attrMap);

}
