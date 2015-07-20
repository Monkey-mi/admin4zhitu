package com.imzhitu.admin.ztworld.service;

import java.util.Map;

import com.hts.web.common.dao.BaseCacheDao;

/**
 * @author zhangbo 2015年7月13日
 *
 */
public interface ADKeywordCacheService extends BaseCacheDao {
    
    /**
     * 从redis缓存中获取广告关键词集合
     * 
     * @author zhangbo 2015年7月17日
     * @return
     */
    public Map<String, Integer> getADKeywordCacheFromRedis();

    /**
     * 刷新广告关键字缓存
     * 
     * @author zhangbo 2015年7月17日
     */
    public void refreshADKeywordCache();
    
    /**
     * redis-cli发布消息，供其他redis-cli接收
     * 
     * @param msg	发布的消息
     * @author zhangbo	2015年7月17日
     */
    public void publisher(String msg);

}
