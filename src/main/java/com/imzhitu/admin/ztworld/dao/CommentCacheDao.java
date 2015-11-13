package com.imzhitu.admin.ztworld.dao;

import com.hts.web.common.dao.BaseCacheDao;

/**
 * 
 * @author zxx 2015年11月12日 11:15:53
 *
 */
public interface CommentCacheDao extends BaseCacheDao{
	/**
	 * 更新htworld_comment_week表在redis中的最大id,可以理解为，当前扫描到的最大ID
	 * @param maxId
	 */
	public void updateCommentWeekMaxIdCache(Integer maxId);
	
	/**
	 * 获取htworld_comment_week表在redis中的最大id,可以理解为，上一次扫描到的最大ID
	 * @return
	 */
	public Integer getCommentWeekMaxIdCache();
}
