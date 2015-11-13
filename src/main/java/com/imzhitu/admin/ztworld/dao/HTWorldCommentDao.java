package com.imzhitu.admin.ztworld.dao;

import java.util.Date;
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
	 * 删掉原来的update valid and shield
	 * @param authorId
	 * @author zxx
	 * @time 2015年11月10日 19:42:24
	 */
	public void deleteCommentByUserId(Integer authorId);
	
	/**
	 * 删掉原来的update valid and shield
	 * @param authorId
	 * @author zxx
	 * @time 2015年11月10日 19:42:24
	 */
	public void deleteCommentById(Integer id);
	
	/**
	 * 从htworld_comment_delete表中删除一条数据，并将这条数据恢复到htworld_comment表中
	 * @param id
	 * @author zxx 2015年11月10日 20:55:11
	 */
	public void recoveryCommentById(Integer id);
	
	/**
	 * 从htworld_comment_delete表中删除符合条件的数据，并将这数据恢复到htworld_comment表中
	 * @author zxx 2015年11月10日 20:55:11
	 * @param authorId
	 */
	public void recoverCommentByUserId(Integer authorId);
	
	/**
	 * 根据id查询world_id
	 * 
	 * @param id
	 * @return
	 */
	public Integer queryWorldId(Integer id);
	
	/**
	 * 根据作者id查询world_ids
	 * @param authorId
	 * @return
	 */
	public List<Integer> queryWorldIds(Integer authorId);
	
	/**
	 * 根据时间查询最小Id
	 * @param date
	 * @return
	 * @author zxx 2015年11月12日 10:04:21
	 */
	public Integer queryCommentMinIdByDate(Date date);
	
	/**
	 * 将评论归档到最新一周的评论表中。即：将htworld_comment表中的最新的数据同步到htworld_comment_week 中去
	 * @param minId 最小Id，不包含这个id对应的数据
	 * @author zxx 2015年11月11日 18:21:12
	 */
	public void fileCommentToWeek(Integer minId); 
	
	/**
	 * @author zxx 2015年11月12日 10:04:21
	 * 查询htworld_comment_week中最大的Id
	 * @return
	 */
	public Integer queryCommentWeekMaxId();
	
	/**
	 * 查询htworld_comment_week中最小的Id，目的是当redis宕机后，数据能确保正确
	 * @param date
	 * @return
	 * @author zxx 2015年11月12日 10:04:21
	 */
	public Integer queryCommentWeekMinIdByDate(Date date);
	
	
}
