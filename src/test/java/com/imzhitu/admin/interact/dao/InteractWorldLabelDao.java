package com.imzhitu.admin.interact.dao;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.ztworld.dao.impl.HTWorldLabelWorldDaoImpl;

public class InteractWorldLabelDao extends BaseTest{
	
	@Autowired
	private HTWorldLabelWorldDaoImpl dao;
	
	@Test
	public void testQueryLabelIds(){
		List<Integer> list = dao.queryLabelIds(11790);
		logger.debug(list);
	}

}
