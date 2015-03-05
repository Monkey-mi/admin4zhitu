package com.imzhitu.admin.op.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpActivityLogo;
import com.imzhitu.admin.op.dao.ActivityLogoCacheDao;
import com.imzhitu.admin.op.dao.ActivityLogoDao;

@Repository
public class ActivityLogoCacheDaoImpl extends BaseCacheDaoImpl<OpActivityLogo> implements
		ActivityLogoCacheDao {

	@Autowired
	private ActivityLogoDao activityLogoDao;
	
	@Override
	public void updateLogoCache() {
		List<OpActivityLogo> list = activityLogoDao.queryValidLogo();
		if(getRedisTemplate().hasKey(CacheKeies.OP_ACTIVITY_LOGO)) {
			getRedisTemplate().delete(CacheKeies.OP_ACTIVITY_LOGO);
		}
		if(list.size() > 0) {
			OpActivityLogo[] logos = new OpActivityLogo[list.size()];
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.OP_ACTIVITY_LOGO, 
					list.toArray(logos));
		}
	}

}
