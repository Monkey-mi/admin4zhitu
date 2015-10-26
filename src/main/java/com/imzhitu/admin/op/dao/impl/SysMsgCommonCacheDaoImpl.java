package com.imzhitu.admin.op.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpSysMsgDto;
import com.imzhitu.admin.op.dao.SysMsgCommonCacheDao;
import com.imzhitu.admin.op.mapper.SysMsgCommonMapper;

@Repository
public class SysMsgCommonCacheDaoImpl extends BaseCacheDaoImpl<OpSysMsgDto>implements SysMsgCommonCacheDao {

	@Autowired
	private SysMsgCommonMapper msgCommonMapper;
	
	@Override
	public void updateCache() {
		List<OpSysMsgDto> list = msgCommonMapper.queryCacheMsg(200);
		if(getRedisTemplate().hasKey(CacheKeies.OP_MSG_COMMON_SYSMSG)) {
			getRedisTemplate().delete(CacheKeies.OP_MSG_COMMON_SYSMSG);
		}
		if(list.size() > 0) {
			OpSysMsgDto[] objs = new OpSysMsgDto[list.size()];
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.OP_MSG_COMMON_SYSMSG, 
					list.toArray(objs));
		}
	}
	
}
