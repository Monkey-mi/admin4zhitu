package com.imzhitu.admin.op.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpSquareTopic;

/**
 * <p>
 * 广场话题数据访问接口
 * </p>
 * 
 * 创建时间：2013-9-9
 * @author ztj
 *
 */
public interface SquarePushTopicDao extends BaseDao {

	/**
	 * 保存话题
	 * @param topic
	 */
	public void saveTopic(OpSquareTopic topic);
	
	/**
	 * 查询广场推送话题列表
	 * 
	 * @param rowSelection
	 * @return
	 */
	public List<OpSquareTopic> queryTopic(RowSelection rowSelection);
	
	/**
	 * 查询广场推送话题总数
	 * 
	 * @return
	 */
	public long queryTopicCount();
	
	/**
	 * 根据id删除话题
	 * 
	 * @param id
	 */
	public void deleteTopicById(Integer id);
}
