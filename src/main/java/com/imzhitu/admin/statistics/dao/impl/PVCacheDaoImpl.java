package com.imzhitu.admin.statistics.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.imzhitu.admin.statistics.dao.PVCacheDao;
import com.imzhitu.admin.statistics.pojo.StatPvDto;

@Repository
public class PVCacheDaoImpl extends StatBaseCacheDaoImpl<Long>implements PVCacheDao {

	private static final String STAT_PREFIX = "stat:pv:";
	
	@Override
	public List<StatPvDto> queryAllPv() {
		List<StatPvDto> list = new ArrayList<StatPvDto>();
		ValueOperations<String, Long> vops = getStatRedisTemplate().opsForValue();
		Set<String> keies = getStatRedisTemplate().keys(STAT_PREFIX + "*");
		Date now = new Date();
		StatPvDto pv;
		String s;
		for(String key : keies) {
			pv = getPvDtoByKey(key);
			s = vops.get(key, 0, -1);
			pv.setPv(Long.valueOf(s));
			pv.setPvtime(now.getTime());
			
			list.add(pv);
		}
		return list;
	}
	
	@Override
	public Set<String> queryAllPvKey() {
		return getStatRedisTemplate().keys(STAT_PREFIX + "*");
	}

	@Override
	public void incPv(Integer key, Integer subkey, long pv) {
		getStatRedisTemplate().opsForValue().increment(STAT_PREFIX + key + ":" + subkey, pv);
	}

	@Override
	public void clearPv(Integer key, Integer subkey) {
		getStatRedisTemplate().delete(STAT_PREFIX + key + ":" + subkey);
	}

	@Override
	public StatPvDto getPvDtoByKey(String cacheKey) {
		String[] s;
		StatPvDto pv;
		
		s = cacheKey.split(":");
		pv = new StatPvDto();
		pv.setPvkey(Integer.parseInt(s[2]));
		if(s.length > 3)
			pv.setSubkey(Integer.parseInt(s[3]));
		
		return pv;
	}
	
	@Override
	public Long getPv(String cacheKey) {
		String s = getStatRedisTemplate().opsForValue().get(cacheKey, 0, -1);
		if(s != null && !s.equals("")) {
			return Long.valueOf(s);
		}
		return 0l;
	}

	@Override
	public void addReadTimePv(List<StatPvDto> list) {
		if(list != null && list.size() > 0) {
			
			Calendar ca = Calendar.getInstance();
			ca.set(Calendar.HOUR_OF_DAY, 0);
			ca.set(Calendar.MINUTE, 0);
			ca.set(Calendar.SECOND, 0);
			ca.set(Calendar.MILLISECOND, 0);
			long now = ca.getTimeInMillis();
			List<String> pvids = new ArrayList<String>();
			Map<String, Integer> idxMap = new HashMap<String, Integer>();
			
			StatPvDto pv;
			String cacheKey;
			
			long cachepv;
			long currpv;
			int idx;
			String s;
			
			for(int i = 0; i < list.size(); i++) {
				pv = list.get(i);
				if(!pv.getPvtime().equals(now))
					continue;
				cacheKey = STAT_PREFIX + pv.getPvkey() + ":" + pv.getSubkey();
				pvids.add(cacheKey);
				idxMap.put(cacheKey, i);
			}
			
			if(!pvids.isEmpty()) {
				ValueOperations<String, Long> vops = getStatRedisTemplate().opsForValue();
				for(String key : pvids) {
					s = vops.get(key, 0, -1);
					if(s != null && !s.equals(""))
						cachepv = Long.valueOf(s);
					else
						cachepv = 0l;
					if(cachepv > 0) {
						idx = idxMap.get(key);
						currpv = list.get(idx).getPv();
						list.get(idx).setPv(currpv + cachepv);
					}
					
				}
			}
			
				
		}
	}

}
