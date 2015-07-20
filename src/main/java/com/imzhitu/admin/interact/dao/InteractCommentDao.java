package com.imzhitu.admin.interact.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.InteractComment;

public interface InteractCommentDao extends BaseDao {

	/**
	 * 保存评论
	 * 
	 * @param comment
	 */
	public void saveComment(InteractComment comment);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public InteractComment queryCommentById(Integer id);
	
	public void updateComment(Integer id, String content, Integer labelId);
	
	/**
	 * 根据分页索引查询id
	 * @param labelId
	 * @param page
	 * @return
	 */
	@Deprecated
	public Integer queryIdByPageIndex(int labelId, int page);
	
	/**
	 * 随机查询n条评论
	 * @param labelId
	 * @param n
	 * @return
	 */
	public List<Integer> queryNRandomComment(Integer labelId,int n);
	
	/**
	 * 查询评论列表
	 * 
	 * @param labelId
	 * @param comment
	 * @return
	 */
	public List<InteractComment> queryComment(Integer labelId, String comment, RowSelection rowSelection);
	
	/**
	 * 查询评论列表
	 * 
	 * @param labelId
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<InteractComment> queryComment(Integer labelId, String comment, Integer maxId, RowSelection rowSelection);
	
	/**
	 * 查询评论总数
	 * 
	 * @param labelId
	 * @return
	 */
	public long queryCommentTotal(Integer labelId);
	
	/**
	 * 查询评论总数
	 * 
	 * @param labelId
	 * @param comment
	 * @param maxId
	 * @return
	 */
	public long queryCommentTotal(Integer labelId, String comment, Integer maxId);
	
	/**
	 * 根据ids删除评论
	 * 
	 * @param ids
	 */
	public void deleteByIds(Integer[] ids);
	
	/**
	 * 增加使用次数
	 * 
	 * @param id
	 * @param count
	 */
	public void addCount(Integer id, Integer count);
	
	/**
	 * 修改评论内容
	 * @param content
	 * @param id
	 */
	public void updateCommentContentById(String content,Integer id);
	
}
