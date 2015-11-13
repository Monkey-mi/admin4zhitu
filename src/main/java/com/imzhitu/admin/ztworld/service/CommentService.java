package com.imzhitu.admin.ztworld.service;

import java.util.Date;

import com.hts.web.common.service.BaseService;

/**
 * 
 * 评论模块service
 * @author zxx 2015年11月12日 14:52:29
 *
 */
public interface CommentService extends BaseService{
	/**
	 * 归档
	 */
	public void doFileCommentToWeek();
	
	/**
	 * 根据时间查询最小的Id
	 * @param date
	 * @return
	 */
	public Integer queryCommentWeekMinId(Date date);
}
