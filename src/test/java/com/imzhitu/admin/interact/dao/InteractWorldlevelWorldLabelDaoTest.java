package com.imzhitu.admin.interact.dao;

import java.util.Date;

import com.imzhitu.admin.base.BaseTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import com.imzhitu.admin.common.pojo.InteractWorldlevelWorldLabel;

public class InteractWorldlevelWorldLabelDaoTest extends BaseTest{
	
	@Autowired
	private InteractWorldlevelWorldLabelDao dao;
	
//	@Test
	public void addInteractWorldlevelWorldLabelTest()throws Exception{
		dao.addWorldlevelWorldLabel(1, 1, 1, new Date());
	}
	
	@Test
	public void queryInteractWorldlevelWorldLabelTest()throws Exception {
		List<InteractWorldlevelWorldLabel> list = dao.queryWorldlevelWorldLabelByWid(1);
		logger.info(list.toString());
	}
}
