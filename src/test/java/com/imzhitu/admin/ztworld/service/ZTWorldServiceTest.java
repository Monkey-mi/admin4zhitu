package com.imzhitu.admin.ztworld.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.Tag;
import com.imzhitu.admin.base.BaseTest;


/**
 * <p>
 * 织图管理业务逻辑访问对象单元测试
 * </p>
 * 
 * 创建时间：2014-1-3
 * @author ztj
 *
 */
public class ZTWorldServiceTest extends BaseTest {
	
	@Autowired
	private ZTWorldService service;
	
	@Test
	public void testBuildWorlds() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
//		service.buildWorld(100000, 1, 10, null,
//				null, null, null, null, null,Tag.TRUE, Tag.FALSE, null, null, jsonMap);
		service.buildWorld(0, 1, 10, null,
				null, null, null, null, null,Tag.TRUE, Tag.FALSE,"哈哈", null, 22,null, null, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testUpdateLatestValid() throws Exception {
		service.updateLatestValid(11612, Tag.FALSE);
	}
	
	@Test
	public void testUpdateFilterLogoCache() throws Exception {
		service.updateFilterLogo(2.0907f, "http://static.imzhitu.com/world/thumbs/14060233330003.png", "滤镜来自Instagram", Tag.TRUE);
	}
	
	@Test
	public void updateLatestInvalidTest() throws Exception {
		service.updateLatestInvalid(485);
	}
	
	@Test
	public void queryCommentAtTest() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.queryCommentAt(28226, jsonMap);
	}
}
