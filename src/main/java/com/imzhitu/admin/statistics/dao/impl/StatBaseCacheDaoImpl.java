package com.imzhitu.admin.statistics.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 统计缓存基础类
 * 
 * @author lynch 2015-12-19
 *
 */
public class StatBaseCacheDaoImpl<T> {

	@Autowired
	private RedisTemplate<String, T> statRedisTemplate;

	public RedisTemplate<String, T> getStatRedisTemplate() {
		return statRedisTemplate;
	}

	public void setStatRedisTemplate(RedisTemplate<String, T> statRedisTemplate) {
		this.statRedisTemplate = statRedisTemplate;
	}

}
