package com.imzhitu.admin.interact.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

import java.util.Date;
import java.util.List;

import com.imzhitu.admin.common.pojo.InteractWorldCommentDto;

public class InteractWorldCommentDaoTest extends BaseTest{
	
	@Autowired
	private InteractWorldCommentDao dao;
	
	@Test
	public void queryUnfinishCommentTest()throws Exception{
		Date now = new Date();
		Date begin = new Date(now.getTime() - 1*24*60*60*1000);
		Date end = new Date(now.getTime() + 1*24*60*60*1000);
		List<InteractWorldCommentDto> list = dao.queryUnFinishedComment(begin, end);
		logger.info(list);
	}
}
