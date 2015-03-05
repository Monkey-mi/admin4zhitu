package com.imzhitu.admin.ztworld.dao;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowCallback;
import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.OpActivityWorldValidDto;

public class HTWorldLabelWorldDaoTest extends BaseTest {
	
	private static Logger logger = Logger.getLogger(HTWorldLabelWorldDaoTest.class);

	@Autowired
	private HTWorldLabelWorldDao dao;
	
	@Test
	public void testQueryWorldIds() {
		dao.queryWorldIds(new Integer[]{14760, 14759, 14757, 14756}, Tag.WORLD_LABEL_ACTIVITY, 
				new RowCallback<OpActivityWorldValidDto>() {
			
			@Override
			public void callback(OpActivityWorldValidDto t) {
				logger.debug(t.getWorldId() + " : " + t.getValid());
			}
		});
	}
}
