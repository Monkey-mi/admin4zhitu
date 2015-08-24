package com.imzhitu.admin.ztworld.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.Tag;
import com.imzhitu.admin.base.BaseTest;

/**
 * <p>
 * 织图分类标签管理业务逻辑访问接口单元测试
 * </p>
 * 
 * 创建时间：2014-1-17
 * @author ztj
 *
 */
public class ZTWorldTypeServiceTest extends BaseTest {

	@Autowired
	private ZTWorldTypeService service;
	
	@Test
	public void testBuildTypeWorld() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildTypeWorld("2014-06-09","2014-06-16",0, 1, null, null, null, null, null,null,0, 1, 10, jsonMap);
		logObj(jsonMap);
	}
	
//	@Test
	public void testBatchUpdateRecommendTypeWorldValid() throws Exception {
		service.batchUpdateRecommendTypeWorldValid();
	}
	
	@Test
	public void testBatchUpdateTypeValid() throws Exception {
		service.batchUpdateTypeValid("1,2", Tag.TRUE);
	}
	
	@Test
	public void testAddUpdateTypeWorldSerialSchedula()throws Exception{
//		service.addUpdateTypeWorldSerialSchedula(new String[]{"11918","11855"}, new Date());
	}
	
	@Test
	public void testPerformTypeWorldSchedula()throws Exception{
		service.performTypeWorldSchedula();
	}
	
	@Test
	public void updateTypeWorldCacheTest() throws Exception {
		service.updateTypeWorldCache();
	}
	
}
