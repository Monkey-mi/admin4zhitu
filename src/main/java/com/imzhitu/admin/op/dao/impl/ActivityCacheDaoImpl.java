package com.imzhitu.admin.op.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.HTWorldStickerDto;
import com.hts.web.common.pojo.OpActivity;
import com.hts.web.common.pojo.OpActivityAward;
import com.hts.web.common.pojo.OpActivitySponsor;
import com.imzhitu.admin.op.dao.ActivityAwardDao;
import com.imzhitu.admin.op.dao.ActivityCacheDao;
import com.imzhitu.admin.op.dao.ActivityDao;
import com.imzhitu.admin.op.dao.ActivitySponsorDao;
import com.imzhitu.admin.op.dao.ActivityStarCacheDao;

@Repository
public class ActivityCacheDaoImpl extends BaseCacheDaoImpl<OpActivity> implements
		ActivityCacheDao {

	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private ActivitySponsorDao activitySponsorDao;
	
	@Autowired
	private ActivityAwardDao activityAwardDao;
	
	@Autowired
	private ActivityStarCacheDao starCacheDao;
	
	@Autowired
	private com.hts.web.ztworld.dao.HTWorldStickerDao webStickerDao;
	

	@Override
	public void updateCacheActivity(int limit) {
		List<OpActivity> list = activityDao.queryActivity(new RowSelection(1, limit));
		extractSponsorInfo(list);
		if(getRedisTemplate().hasKey(CacheKeies.OP_ACTIVITY)) {
			getRedisTemplate().delete(CacheKeies.OP_ACTIVITY);
		}
		
		if(getRedisTemplate().hasKey(CacheKeies.OP_ACTIVITY_HASH)) {
			getRedisTemplate().delete(CacheKeies.OP_ACTIVITY_HASH);
		}
		if(list.size() > 0) {
			OpActivity[] oas = new OpActivity[list.size()];
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.OP_ACTIVITY, list.toArray(oas));
			HashOperations<String, String, OpActivity> hashOp = getRedisTemplate().opsForHash();
			for(OpActivity act : list) {
				hashOp.put(CacheKeies.OP_ACTIVITY_HASH, act.getActivityName(), act);
			}
			starCacheDao.deleteStarByActs(oas);
		}
	}

	/**
	 * 获取发起人信息
	 * 
	 * @param activityList
	 * @throws Exception
	 */
	public void extractSponsorInfo(final List<OpActivity> activityList) {
		int size = activityList.size();
		if(size > 0) {
			Integer[] activityIds = new Integer[size];
			final Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
			for (int i = 0; i < size; i++) {
				Integer activityId = activityList.get(i).getId();
				activityIds[i] = activityId;
				indexMap.put(activityId, i);
			}
			if(indexMap.size() > 0) {
				webStickerDao.queryStickerByLabelIds(activityIds, new RowCallback<HTWorldStickerDto>() {
	
					@Override
					public void callback(HTWorldStickerDto t) {
						Integer actId = t.getLabelId();
						Integer index = indexMap.get(actId);
						activityList.get(index).setSticker(t);
					}
					
				});
				activitySponsorDao.querySponsor(activityIds, new RowCallback<OpActivitySponsor>() {
	
					@Override
					public void callback(OpActivitySponsor t) {
						Integer actId = t.getActivityId();
						Integer index = indexMap.get(actId);
						activityList.get(index).getSponsors().add(t);
					}
				});
				
				activityAwardDao.queryAwards(activityIds, new RowCallback<OpActivityAward>() {
	
					@Override
					public void callback(OpActivityAward t) {
						Integer actId = t.getActivityId();
						Integer index = indexMap.get(actId);
						activityList.get(index).getAwards().add(t);
					}
				});
			}
		}
	}

}
