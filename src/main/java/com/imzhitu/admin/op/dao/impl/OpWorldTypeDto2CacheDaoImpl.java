package com.imzhitu.admin.op.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpWorldTypeDto;
import com.imzhitu.admin.op.dao.OpWorldTypeDto2CacheDao;

@Repository
public class OpWorldTypeDto2CacheDaoImpl extends BaseCacheDaoImpl<OpWorldTypeDto> implements
		OpWorldTypeDto2CacheDao {

	@Autowired
	private com.hts.web.operations.dao.SquarePushDao webSquarePushDao;

	@Override
	public void updateWorldTypeDto2(int limit) {
		List<OpWorldTypeDto> dtoList = webSquarePushDao.querySuperbFromMaster(new RowSelection(1, limit));
		List<OpWorldTypeDto> weightList = webSquarePushDao.queryWeightSuperbFromMaster(limit);
		if(weightList.size() >  0) {
			
			List<Integer> remIdxs = new ArrayList<Integer>(); // 待删除的索引
			Set<Integer> wset = new HashSet<Integer>();
			for(OpWorldTypeDto dto : weightList) {
				wset.add(dto.getId());
			}
			
			// 加载被删除的索引
			for(int i = 0; i < dtoList.size(); i++) {
				if(wset.contains(dtoList.get(i).getId())) {
					remIdxs.add(i);
				}
			}
			
			// 删除与置顶冲突的数据
			if(!remIdxs.isEmpty()) {
				for(Integer idx : remIdxs) {
					dtoList.remove(idx);
				}
			}
			
		}
		
		updateWeightList(weightList);
		
		if(getRedisTemplate().hasKey(CacheKeies.OP_SUPERB_TYPE)) {
			getRedisTemplate().delete(CacheKeies.OP_SUPERB_TYPE);
		}
		OpWorldTypeDto[] list = new OpWorldTypeDto[dtoList.size()];
		getRedisTemplate().opsForList().rightPushAll(CacheKeies.OP_SUPERB_TYPE, dtoList.toArray(list));

	}
	
	/**
	 * 更新置顶缓存列表
	 * 
	 * @param weightList
	 */
	private void updateWeightList(List<OpWorldTypeDto> weightList) {
		if(getRedisTemplate().hasKey(CacheKeies.OP_SUPERB_TYPE_WEIGHT)) {
			getRedisTemplate().delete(CacheKeies.OP_SUPERB_TYPE_WEIGHT);
		}
		OpWorldTypeDto[] list = new OpWorldTypeDto[weightList.size()];
		getRedisTemplate().opsForList().rightPushAll(CacheKeies.OP_SUPERB_TYPE_WEIGHT, weightList.toArray(list));
	}
	
}
