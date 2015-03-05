package com.imzhitu.admin.ztworld.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.HTWorldLabel;
import com.imzhitu.admin.ztworld.dao.HTWorldLabelCacehDao;
import com.imzhitu.admin.ztworld.dao.HTWorldLabelDao;

@Repository
public class HTWorldLabelCacheDaoImpl extends BaseCacheDaoImpl<HTWorldLabel> implements
		HTWorldLabelCacehDao {

	@Autowired
	private HTWorldLabelDao worldLabelDao;
	
	
	@Override
	public void updateHotLabel(int limit) {
		List<HTWorldLabel> labelList = worldLabelDao.queryLabel(Tag.WORLD_LABEL_NORMAL, 
				new RowSelection(1, 10));
		if(getRedisTemplate().hasKey(CacheKeies.ZTWORLD_LABEL_HOT)) {
			getRedisTemplate().delete(CacheKeies.ZTWORLD_LABEL_HOT);
		}
		if(limit > 0) {
			HTWorldLabel[] labels = new HTWorldLabel[labelList.size()];
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.ZTWORLD_LABEL_HOT, labelList.toArray(labels));
		}
	}

	@Override
	public void updateActivityLabel(int limit) {
		List<HTWorldLabel> labelList = worldLabelDao.queryLabel(Tag.WORLD_LABEL_ACTIVITY,
				new RowSelection(1, 10));
		if(getRedisTemplate().hasKey(CacheKeies.ZTWORLD_LABEL_ACTIVITY)) {
			getRedisTemplate().delete(CacheKeies.ZTWORLD_LABEL_ACTIVITY);
		}
		if(limit > 0) {
			HTWorldLabel[] labels = new HTWorldLabel[labelList.size()];
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.ZTWORLD_LABEL_ACTIVITY, labelList.toArray(labels));
		}
	}
	
}
