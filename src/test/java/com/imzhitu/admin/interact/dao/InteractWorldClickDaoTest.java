package com.imzhitu.admin.interact.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.InteractWorldClick;

import java.util.Date;
import java.util.List;
public class InteractWorldClickDaoTest extends BaseTest{
	
	@Autowired
	private InteractWorldClickDao dao;
	
	@Test
	public void queryUnfinishClickTest()throws Exception{
//		Date now = new Date();
//		Date begin = new Date(now.getTime() - 1*24*60*60*1000);
//		Date end = new Date(now.getTime() + 1*24*60*60*1000);
//		List<InteractWorldClick> list = dao.queryUnFinishedClick(begin, end);
//		logger.info(list);
	}
}
