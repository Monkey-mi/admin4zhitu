package com.imzhitu.admin.channel.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;

/**
 * 频道缓存数据操作类
 * 
 * @author zhangbo	2015年12月4日
 *
 */
@Service
public class ChannelCache extends BaseCacheDaoImpl<com.hts.web.common.pojo.OpChannel> {
	
	/**
	 * 更新频道缓存
	 * 
	 * @param clist	频道集合（频道对象为web端com.hts.web.common.pojo.OpChannel，不然客户端调用反序列化时会不正确）
	 * @author zhangbo	2015年12月4日
	 */
	public void updateChannel(List<com.hts.web.common.pojo.OpChannel> clist) {
		
		if(getRedisTemplate().hasKey(CacheKeies.OP_CHANNEL)) {
			getRedisTemplate().delete(CacheKeies.OP_CHANNEL);
		}
		if(clist.size() > 0) {
			com.hts.web.common.pojo.OpChannel[] list = new com.hts.web.common.pojo.OpChannel[clist.size()];
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.OP_CHANNEL, clist.toArray(list));
		}
	}
	
	/**
	 * 更新推荐频道缓存
	 * 
	 * @param clist	推荐频道集合（频道对象为web端com.hts.web.common.pojo.OpChannel，不然客户端调用反序列化时会不正确）
	 * @author zhangbo	2015年12月4日
	 */
	public void updateRecommendChannel(List<com.hts.web.common.pojo.OpChannel> clist) {
		
		if(getRedisTemplate().hasKey(CacheKeies.OP_CHANNEL_RECOMMEND)) {
			getRedisTemplate().delete(CacheKeies.OP_CHANNEL_RECOMMEND);
		}
		if(clist.size() > 0) {
			com.hts.web.common.pojo.OpChannel[] list = new com.hts.web.common.pojo.OpChannel[clist.size()];
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.OP_CHANNEL_RECOMMEND, clist.toArray(list));
		}
	}
}
