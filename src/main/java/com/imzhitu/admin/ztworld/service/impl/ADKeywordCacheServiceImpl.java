package com.imzhitu.admin.ztworld.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.stereotype.Service;

import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.ADKeywordDTO;
import com.imzhitu.admin.ztworld.mapper.ADCommentMapper;
import com.imzhitu.admin.ztworld.service.ADKeywordCacheService;

/**
 * @author zhangbo	2015年7月17日
 *
 */
@Service
public class ADKeywordCacheServiceImpl extends BaseCacheDaoImpl<Map<String, Integer>> implements ADKeywordCacheService {
    
    /**
     * 发布的频道名称
     * @author zhangbo	2015年7月17日
     */
    private static final String channelTopic = "AdminToDaemon"; 
    
    @Autowired
    private ADCommentMapper mapper;

    @Override
    public Map<String, Integer> getADKeywordCacheFromRedis() {
	
	Map<String, Integer> map = new HashMap<String, Integer>();
	
	BoundValueOperations<String, Map<String, Integer>> bound = getRedisTemplate().boundValueOps(Admin.ADMIN_DAEMON_ADCACHEKEY);
	if (bound.get() == null) {
	    List<ADKeywordDTO> list = mapper.queryADKeywordList(new ADKeywordDTO());
	    for (ADKeywordDTO dto : list) {
		map.put(dto.getADKeyword(), dto.getHitCount());
	    }
	    bound.set(map);
	
	} else {
	    map = bound.get();
	}

	return map;
    }

    @Override
    public void refreshADKeywordCache() {
	
	// 先把当前的redis数据持久化到数据库，然后再重新获取
	Map<String, Integer> adkeywordMap = getADKeywordCacheFromRedis();
	for (String keyword : adkeywordMap.keySet()) {
	    ADKeywordDTO dto = new ADKeywordDTO();
	    dto.setADKeyword(keyword);
	    dto.setHitCount(adkeywordMap.get(keyword));
	    mapper.updateADKeyword(dto);
	}
	
	List<ADKeywordDTO> list = mapper.queryADKeywordList(new ADKeywordDTO());
	Map<String, Integer> map = new HashMap<String, Integer>();
	for (ADKeywordDTO adKeywordDTO : list) {
	    map.put(adKeywordDTO.getADKeyword(), adKeywordDTO.getHitCount());
	}
	
	// 刷新redis缓存
	BoundValueOperations<String, Map<String, Integer>> bound = getRedisTemplate().boundValueOps(Admin.ADMIN_DAEMON_ADCACHEKEY);
	bound.set(map);
	
    }

    @Override
    public void publisher(String msg) {
	getRedisTemplate().convertAndSend(channelTopic, msg);
    }

}
