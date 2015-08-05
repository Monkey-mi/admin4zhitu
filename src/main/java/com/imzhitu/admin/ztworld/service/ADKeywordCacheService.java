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
    Map<String, Integer> getADKeywordCacheFromRedis();
    
    /**
     * 把redis缓存中广告关键词集合持久化到DB
     * @author zhangbo	2015年8月5日
     */
    void setADKeywordCacheToDB();

    /**
     * 从数据库中刷新广告关键字集合到redis缓存
     * 
     * @author zhangbo 2015年7月17日
     */
    void refreshADKeywordCacheToRedis();
    
    /**
     * redis-cli发布消息，供其他redis-cli接收
     * 
     * @param msg	发布的消息
     * @author zhangbo	2015年7月17日
     */
    void publisher(String msg);

}
