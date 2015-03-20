package com.imzhitu.admin.interact.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.InteractCommentLabel;
import com.imzhitu.admin.common.pojo.InteractCommentLabelTree;

/**
 * <p>
 * 评论标签数据访问接口
 * </p>
 * 
 * 创建时间：2013-11-22
 * @author ztj
 *
 */
public interface InteractCommentLabelDao extends BaseDao {
	
	/**
	 * 查询是否存在该名字的标签
	 * true 存在
	 * false 不存在
	 */
	public boolean checkLabelExsistByLabelName(String labelName);

	/**
	 * 保存标签
	 * 
	 * @param label
	 */
	public void saveLabel(InteractCommentLabel label);
	
	/**
	 * 查询所有标签
	 * @return
	 */
	public List<InteractCommentLabel> queryLabel();
	
	/**
	 * 查询标签
	 * @param groupId
	 * @param rowSelection
	 * @return
	 */
	public List<InteractCommentLabel> queryLabel(Integer groupId, RowSelection rowSelection);
	
	/**
	 * 查询标签
	 * @param maxId
	 * @param groupId
	 * @param rowSelection
	 * @return
	 */
	public List<InteractCommentLabel> queryLabel(Integer maxId, Integer groupId, RowSelection rowSelection);
	
	/**
	 * 查询标签总数
	 * @param maxId
	 * @param groupId
	 * @return
	 */
	public long queryLabelCount(Integer maxId, Integer groupId);
	
	/**
	 * 查询所有标签分组
	 * 
	 * @return
	 */
	public List<InteractCommentLabel> queryLabelGroup(); 
	
	/**
	 * 查询标签分组
	 * @param rowSelection
	 * @return
	 */
	public List<InteractCommentLabel> queryLabelGroup(RowSelection rowSelection);
	
	/**
	 * 查询标签分组
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<InteractCommentLabel> queryLabelGroup(Integer maxId, RowSelection rowSelection);
	
	/**
	 * 查询标签分组总数
	 * @param maxId
	 * @return
	 */
	public long queryLabelGroupCount(Integer maxId);
	
	/**
	 * 更新标签
	 * 
	 * @param id
	 * @param labelName
	 * @param groupId
	 */
	public void updateLabel(Integer id, String labelName, Integer groupId);
	
	/**
	 * 根据分组id删除
	 * 
	 * @param groupId
	 */
	public void deleteByGroupId(Integer groupId);
	
	/**
	 * 查询分组树
	 * 
	 * @return
	 */
	public List<InteractCommentLabelTree> queryLabelGroupTree(Integer selectId);
	
	/**
	 * 查询标签树
	 * 
	 * @return
	 */
	public List<InteractCommentLabelTree> queryLabelTree(Integer groupId, Integer selectId);
	
	
}
