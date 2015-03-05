package com.imzhitu.admin.op.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpActivity;
import com.hts.web.userinfo.service.UserInfoService;
import com.imzhitu.admin.common.pojo.OpActivityStar;
import com.imzhitu.admin.op.dao.ActivityStarCacheDao;
import com.imzhitu.admin.op.mapper.ActivityStarMapper;

@Repository
public class ActivityStarCacheDaoImpl extends BaseCacheDaoImpl<com.hts.web.common.pojo.OpActivityStar> implements
		ActivityStarCacheDao {

	@Autowired
	private ActivityStarMapper activityStarMapper;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Override
	public void updateStar(Integer activityId, Integer limit) {
		OpActivityStar star = new OpActivityStar();
		star.setActivityId(activityId);
		star.setFirstRow(0);
		star.setLimit(limit);
		//查询置顶明星列表
		List<com.hts.web.common.pojo.OpActivityStar> cacheList = 
					activityStarMapper.queryCacheStar(star);
		userInfoService.extractVerify(cacheList);
		HashOperations<String, Integer, List<com.hts.web.common.pojo.OpActivityStar>> ops = 
				getRedisTemplate().opsForHash();
		ops.put(CacheKeies.OP_ACTIVITY_STAR, star.getActivityId(), cacheList);
	}

	@Override
	public void deleteStarByActs(OpActivity[] acts) {
		Set<Integer> deleteKeyCollector = new HashSet<Integer>();
		
		Set<Integer> newActSet = new HashSet<Integer>();
		for(OpActivity na : acts) {
			newActSet.add(na.getId());
		}
		BoundHashOperations<String, Integer, List<com.hts.web.common.pojo.OpActivityStar>> ops = 
				getRedisTemplate().boundHashOps(CacheKeies.OP_ACTIVITY_STAR);
		
		Set<Integer> oldActSet = ops.keys();
		for(Integer oa : oldActSet) {
			if(!newActSet.contains(oa)) {
				deleteKeyCollector.add(oa);
			}
		}
		if(!deleteKeyCollector.isEmpty()) {
			ops.delete(deleteKeyCollector.toArray());
		}
		
	}

}
