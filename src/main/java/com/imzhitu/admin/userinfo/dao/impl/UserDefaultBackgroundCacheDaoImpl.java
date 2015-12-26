package com.imzhitu.admin.userinfo.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.imzhitu.admin.userinfo.dao.UserDefaultBackgroundCacheDao;
import com.imzhitu.admin.userinfo.mapper.UserDefaultBackgroundMapper;
import com.imzhitu.admin.userinfo.pojo.DefaultBackground;

/**
 * @author zhangbo	2015年12月25日
 *
 */
@Repository("com.imzhitu.admin.userinfo.dao.impl.UserDefaultBackgroundCacheDaoImpl")
public class UserDefaultBackgroundCacheDaoImpl extends BaseCacheDaoImpl<String> implements UserDefaultBackgroundCacheDao {

	@Autowired
	private UserDefaultBackgroundMapper userDefaultBackgroundMapper;
	
	@Override
	public void updateDefaultBackgroundPath() {
		List<DefaultBackground> list = userDefaultBackgroundMapper.queryDefaultBackground();
		String[] backgrounds = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			backgrounds[i] = list.get(i).getBackground();
		}
		if(getRedisTemplate().hasKey(CacheKeies.USER_DEFAULT_BACKGROUNG)) {
			getRedisTemplate().delete(CacheKeies.USER_DEFAULT_BACKGROUNG);
		}
		getRedisTemplate().opsForList().rightPushAll(CacheKeies.USER_DEFAULT_BACKGROUNG, backgrounds);
	}

}
