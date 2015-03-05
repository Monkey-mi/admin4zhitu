package com.imzhitu.admin.ztworld.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

public class HTWorldStickerTypeCacheDaoTest extends BaseTest {

	@Autowired
	private HTWorldStickerTypeCacheDao dao;
	
	@Test
	public void updateStickerTypeTest() throws Exception {
		dao.updateStickerType();
	}
}
