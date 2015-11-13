package com.imzhitu.admin.ztworld.service.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.ztworld.dao.CommentCacheDao;
import com.imzhitu.admin.ztworld.dao.HTWorldCommentDao;
import com.imzhitu.admin.ztworld.service.CommentService;

/**
 * 
 * @author zxx 2015年11月12日 11:01:34
 * 
 */
@Service
public class CommentServiceImpl extends BaseServiceImpl implements CommentService{
	
	@Autowired
	private CommentCacheDao commentCacheDao;
	
	@Autowired
	private HTWorldCommentDao worldCommentDao;
	
	Logger log = Logger.getLogger(CommentServiceImpl.class);

	/**
	 * 将评论归档到周表中
	 * @author zxx 2015年11月12日 19:50:56
	 */
	@Override
	public void doFileCommentToWeek() {
		try{
			Date now = new Date();
			log.info("begin to doFileCommentToWeek" + now);
			Integer commentWeekMaxId = worldCommentDao.queryCommentWeekMaxId();
			if(commentWeekMaxId == null){
				Date preDate = new Date(now.getTime() - 3L * 24 * 60 * 60 * 1000);
				commentWeekMaxId = worldCommentDao.queryCommentMinIdByDate(preDate);
			}
			
			worldCommentDao.fileCommentToWeek(commentWeekMaxId);
			Date end = new Date();
			log.info("end to doFileCommentToWeek.cost:" + (end.getTime() - now.getTime()) + "ms.");
		}catch(Exception e){
			log.warn(e);
		}
	}

	@Override
	public Integer queryCommentWeekMinId(Date date) {
		return worldCommentDao.queryCommentWeekMinIdByDate(date);
	}
	
	

}
