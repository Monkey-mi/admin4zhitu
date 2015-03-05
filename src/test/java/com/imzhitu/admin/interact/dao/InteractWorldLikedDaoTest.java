package com.imzhitu.admin.interact.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

import java.util.Date;
import java.util.List;

import com.imzhitu.admin.common.pojo.InteractWorldLiked;

public class InteractWorldLikedDaoTest extends BaseTest{
	
	@Autowired
	private InteractWorldLikedDao dao;
	
	@Test
	public void queryUnfinishLikedTest()throws Exception{
		Date now = new Date();
		Date begin = new Date(now.getTime() - 1*24*60*60*1000);
		Date end = new Date(now.getTime() + 1*24*60*60*1000);
		List<InteractWorldLiked> list = dao.queryUnfinishedLiked(begin, end);
		logger.info(list);
	}
}
