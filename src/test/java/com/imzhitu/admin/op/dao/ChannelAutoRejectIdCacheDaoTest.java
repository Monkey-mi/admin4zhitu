package com.imzhitu.admin.op.dao;

import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

/**
 * @author zhangbo	2015年9月17日
 *
 */
public class ChannelAutoRejectIdCacheDaoTest extends BaseTest {
	
	@Autowired
	private ChannelAutoRejectIdCacheDao dao;
	
	@Test
	public void testAddId() {
		dao.addId(11834);
	}
	
	@Test
	public void testGetAutoRejectChannelCache() {
		Set<Integer> autoRejectChannelCache = dao.getAutoRejectChannelCache();
		for (Integer integer : autoRejectChannelCache) {
			System.out.println(integer);
		}
	}

}
