package com.imzhitu.admin.ztworld.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

public class StickerSetDtoCacheDaoTest extends BaseTest {

	@Autowired
	private StickerSetDtoCacheDao dao;
	
	@Test
	public void updateLibTest() {
		dao.updateLib();
	}
}
