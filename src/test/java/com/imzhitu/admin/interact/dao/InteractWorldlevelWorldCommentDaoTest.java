package com.imzhitu.admin.interact.dao;

import com.imzhitu.admin.base.BaseTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import com.imzhitu.admin.common.pojo.InteractWorldlevelWorldComment;

public class InteractWorldlevelWorldCommentDaoTest extends BaseTest{
	
	@Autowired
	private InteractWorldlevelWorldCommentDao dao;
	
//	@Test
	public void addInteractWorldlevelWorldCommentTest()throws Exception{
		dao.addWorldlevelWorldComment(1, 1, 1, new Date());
	}
	
	@Test
	public void queryInteractWorldlevelWorldCommentTest()throws Exception{
		List<InteractWorldlevelWorldComment> list = dao.queryWorldlevelWorldCommentByWid(1);
		for(InteractWorldlevelWorldComment o:list){
			logger.info(o.toString());
		}
	}
}
