package com.imzhitu.admin.ztworld.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.imzhitu.admin.ztworld.dao.CommentCacheDao;
import com.imzhitu.admin.ztworld.dao.HTWorldCommentDao;

@Repository
public class CommentCacheDaoImpl extends BaseCacheDaoImpl<Integer> implements CommentCacheDao{

	@Autowired
	private HTWorldCommentDao commentDao;
	
	@Override
	public void updateCommentWeekMaxIdCache(Integer maxId) {
		if(getRedisTemplate().hasKey(CacheKeies.ZTWORLD_COMMENT_WEEK_MAX_ID)){
			getRedisTemplate().delete(CacheKeies.ZTWORLD_COMMENT_WEEK_MAX_ID);
		}
		getRedisTemplate().opsForValue().set(CacheKeies.ZTWORLD_COMMENT_WEEK_MAX_ID, maxId);
	}

	@Override
	public Integer getCommentWeekMaxIdCache() {
		if(getRedisTemplate().hasKey(CacheKeies.ZTWORLD_COMMENT_WEEK_MAX_ID)){
			return getRedisTemplate().opsForValue().get(CacheKeies.ZTWORLD_COMMENT_WEEK_MAX_ID);
		}else{
			return null;
		}
		
	}

}
