package com.imzhitu.admin.ztworld.service;

import java.io.File;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.ZTWorldSubtitle;

public class SubtitleServiceTest extends BaseTest {

	@Autowired
	private SubtitleService service;
	
	@Test
	public void updateSubtitleCacheTest() throws Exception {
		service.updateSubtitleCache(32);
	}
	
//	@Test
	public void saveSubtitleByFileTest() throws Exception {
		service.saveSubtitleByFile(new File("/home/lynch/t"), "en");
	}
	
	@Test
	public void saveTitleTest() throws Exception {
		ZTWorldSubtitle title = new ZTWorldSubtitle();
		title.setSubtitle("呵呵呵");
		title.setSubtitleEn("hehe");
		title.setTransTo("en");
		service.saveTitle(title);
	}
	
	@Test
	public void updateTitleTest() throws Exception {
		ZTWorldSubtitle title = new ZTWorldSubtitle();
		title.setSubtitle("呵呵呵");
		title.setSubtitleEn("hehe");
		title.setTransTo("en");
		title.setId(2);
		service.updateTitle(title);
	}
	
	@Test
	public void deleteTitleTest() throws Exception {
		service.deleteTitleByIds("3,4");
	}
	
	@Test
	public void queryTitleByIdTest() throws Exception {
		service.queryTitleById(1);
	}
	
	
	
}
