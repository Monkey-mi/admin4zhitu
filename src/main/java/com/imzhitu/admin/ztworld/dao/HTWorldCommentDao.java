package com.imzhitu.admin.ztworld.dao;

import java.util.List;
import java.util.Map;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.ZTWorldCommentDto;

/**
 * <p>
 * 织图评论数据访问接口
 * </p>
 * 
 * 创建时间：2013-8-3
 * @author ztj
 *
 */
public interface HTWorldCommentDao extends BaseDao {

	/**
	 * 条件查询评论列表
	 * 
	 * @param attrMap
	 * @return
	 */
	public List<ZTWorldCommentDto> queryComment(Map<String, Object> attrMap, Map<String, Object> userAttrMap, RowSelection rowSelection);

	/**
	 * 根据最大id条件查询评论列表
	 * 
	 * @param attrMap
	 * @param userAttrMap
	 * @return
	 */
	public List<ZTWorldCommentDto> queryCommentByMaxId(Integer maxId, Map<String, Object> attrMap, Map<String, Object> userAttrMap, RowSelection rowSelection);
	
	/**
	 * 根据最小id条件查询评论列表
	 * @param minId
	 * @param attrMap
	 * @param userAttrMap
	 * @param rowSelection
	 * @return
	 */
	public List<ZTWorldCommentDto> queryCommentByMinId(Integer minId, Map<String, Object> attrMap, Map<String, Object> userAttrMap, RowSelection rowSelection);
	
	/**
	 * 条件查询评论总数
	 * 
	 * @param attrMap
	 * @param userAttrMap
	 * @return
	 */
	public long queryCommentCount(Map<String, Object> attrMap, Map<String, Object> userAttrMap);
	
	/**
	 * 根据最大id条件查询评论总数
	 * 
	 * @param attrMap
	 * @param userAttrMap
	 * @return
	 */
	public long queryCommentCountByMaxId(Integer maxId, Map<String, Object> attrMap, Map<String, Object> userAttrMap);
	
	/**
	 * 根据最小id条件查询评论总数
	 * 
	 * @param minId
	 * @param attrMap
	 * @param userAttrMap
	 * @return
	 */
	public long queryCommentCountByMinId(Integer minId, Map<String, Object> attrMap, Map<String, Object> userAttrMap);
	
	
	/**
	 * 更新评论屏蔽标志
	 * 
	 * @param id
	 * @param shield
	 */
	public void updateCommentShield(Integer id, Integer shield);
	
}
