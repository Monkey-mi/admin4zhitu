package com.imzhitu.admin.ztworld.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.HTWorldSubtitleDto;
import com.imzhitu.admin.ztworld.dao.SubtitleCacheDao;
import com.imzhitu.admin.ztworld.mapper.SubtitleMapper;

@Repository
public class SubtitleCacheDaoImpl extends BaseCacheDaoImpl<HTWorldSubtitleDto> implements
		SubtitleCacheDao {

	@Autowired
	private SubtitleMapper subtitleMapper;
	
	@Override
	public void update(Integer limit) {
		if(getRedisTemplate().hasKey(CacheKeies.ZTWORLD_SUBTITLE)) {
			getRedisTemplate().delete(CacheKeies.ZTWORLD_SUBTITLE);
		}
		List<HTWorldSubtitleDto> list = subtitleMapper.queryCacheSubtitle(limit);
		if(list.size() > 0) {
			HTWorldSubtitleDto[] objs = new HTWorldSubtitleDto[list.size()];
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.ZTWORLD_SUBTITLE, 
					list.toArray(objs));
		}
	}

}
