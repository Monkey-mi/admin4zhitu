package com.imzhitu.admin.channel.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;

/**
 * 频道主题缓存操作类
 * 操作数据为web端的OpChannelTheme
 * 
 * @author zhangbo	2015年12月4日
 *
 */
@Repository
public class ChannelThemeCache extends BaseCacheDaoImpl<com.hts.web.common.pojo.OpChannelTheme> {
	
	/**
	 * 更新频道主题缓存
	 * 
	 * @param ctlist	频道主题集合（频道主题对象为web端com.hts.web.common.pojo.OpChannelTheme，不然客户端调用反序列化时会不正确）
	 * @author zhangbo	2015年12月4日
	 */
	public void updateChannelTheme(List<com.hts.web.common.pojo.OpChannelTheme> ctlist) {
		
		if(getRedisTemplate().hasKey(CacheKeies.OP_CHANNEL_THEME)) {
			getRedisTemplate().delete(CacheKeies.OP_CHANNEL_THEME);
		}
		if(ctlist.size() > 0) {
			com.hts.web.common.pojo.OpChannelTheme[] list = new com.hts.web.common.pojo.OpChannelTheme[ctlist.size()];
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.OP_CHANNEL_THEME, ctlist.toArray(list));
		}
	}
}
