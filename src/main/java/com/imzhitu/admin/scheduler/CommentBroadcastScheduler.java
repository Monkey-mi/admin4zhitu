package com.imzhitu.admin.scheduler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.common.pojo.HTWorldComment;

/**
 * 评论广播计划
 * 
 * @author lynch 2015-11-03
 *
 */
public class CommentBroadcastScheduler {
	
	private static Logger log = Logger.getLogger(CommentBroadcastScheduler.class);
	
	// 标记是否在广播评论
	private boolean broadcasting = false;
	
	@Autowired
	private com.hts.web.ztworld.dao.CommentBroadcastCacheDao webCommentBroadcastCacheDao;
	
	@Autowired
	private com.hts.web.ztworld.service.ZTWorldInteractService webWorldInteractService;
	
	/**
	 * 广播队列
	 */
	public void broadcastCommentQueue() {
		HTWorldComment comment;
		
		if(broadcasting) return;
		
		broadcasting = true;
		
		while((comment = webCommentBroadcastCacheDao.popComment()) != null) {
			try {
				webWorldInteractService.broadcastComment(comment);
			} catch (Exception e) {
				log.warn("broadcast comment error:" + "commentId=" + comment.getId(), e);
			}
		}
		
		broadcasting = false;
	}
}
