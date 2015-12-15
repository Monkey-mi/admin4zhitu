package com.imzhitu.admin.channel.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;

/**
 * 频道缓存数据操作类
 * 
 * @author zhangbo	2015年12月4日
 *
 */
@Repository
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
	 * TODO 此方法是在3.2版本设计，目前这个方法没有被用到，是由于更改了逻辑，在web端调用的时候，推荐频道从CacheKeies.OP_CHANNEL中获取前6位
	 * 不再通过运营系统操作频道置顶来动态设置数量，后续若有需要，再使用，等3.2之前老版本消化后，看是否再重新采用这个redis，或者重新设计
	 * 
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
