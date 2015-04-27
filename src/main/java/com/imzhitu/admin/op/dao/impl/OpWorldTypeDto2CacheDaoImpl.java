package com.imzhitu.admin.op.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpWorldTypeDto2;
import com.imzhitu.admin.op.dao.OpWorldTypeDto2CacheDao;

@Repository
public class OpWorldTypeDto2CacheDaoImpl extends BaseCacheDaoImpl<OpWorldTypeDto2> implements
		OpWorldTypeDto2CacheDao {

	@Autowired
	private com.hts.web.operations.dao.SquarePushDao webSquarePushDao;

	@Override
	public void updateWorldTypeDto2(int limit) {
		List<OpWorldTypeDto2> dtoList = webSquarePushDao.querySuperbSquareIndexFromMaster(new RowSelection(1, limit));
		if(getRedisTemplate().hasKey(CacheKeies.OP_SUPERB_TYPE)) {
			getRedisTemplate().delete(CacheKeies.OP_SUPERB_TYPE);
		}
		OpWorldTypeDto2[] list = new OpWorldTypeDto2[dtoList.size()];
		getRedisTemplate().opsForList().rightPushAll(CacheKeies.OP_SUPERB_TYPE, dtoList.toArray(list));

	}
}
