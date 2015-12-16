package com.imzhitu.admin.op.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.Tag;
import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.op.pojo.NearBulletinDto;

public class OpMsgBulletinServiceTest extends BaseTest{
	
	@Autowired
	private OpMsgBulletinService service;
	
	private Logger log = Logger.getLogger(OpMsgBulletinServiceTest.class);
	
	@Test
	public void insertMsgBulletinTest()throws Exception{
		service.insertMsgBulletin("http://static.imzhitu.com/ios/image/2015/06/15/15/6e59030b0b4a2c977203581e0ca5015d_20991@2x.jpg", 1, "http://www.imzhitu.com", 0,null,null);
	}
	
	@Test
	public void batchDeleteMsgBulletinTest()throws Exception{
		service.batchDeleteMsgBulletin("2,3,4,5,6,7,8,9");
	}
	
	@Test
	public void updateMsgBulletinTest()throws Exception{
		service.updateMsgBulletin(1, "http://static.imzhitu.com/ios/image/2015/06/15/15/6e59030b0b4a2c977203581e0ca5015d_20991@2x.jpg", 1, "http://www.imzhitu.com", 1, 14,null,null);
	}
	
	@Test
	public void batchUpdateMsgBulletinValid()throws Exception{
		service.batchUpdateMsgBulletinValid("1,2,3,4",Tag.TRUE,14);
	}
	
	@Test
	public void queryMsgBulletinTest()throws Exception{
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		service.queryMsgBulletin(null, null, null, null, null, 1, 10, jsonMap);
		log.info(jsonMap);
	}
	
	@Test
	public void queryMsgBulletinTotalCount()throws Exception{
		long total = service.queryMsgBulletinTotalCount(null, null, null, null);
		log.info("===========total count:"+total);
	}
	
	@Test
	public void updateMsgBulletinCacheTest()throws Exception{
		service.updateMsgBulletinCache("32,33,34,35,36,37,38,39,40", 0);
	}
	
	@Test
	public void saveNearBulletinTest() throws Exception {
		NearBulletinDto dto = new NearBulletinDto();
		dto.setBulletinName("哈哈哈哈");
		dto.setBulletinPath("http://sdsdf");
		dto.setBulletinType(1);
		List<Integer> cityIds = new ArrayList<Integer>();
		cityIds.add(19795);
		dto.setCityIds(cityIds);
		service.saveNearBulletin(dto);
	}
	
	@Test
	public void updateNearBulletinTest() throws Exception {
		NearBulletinDto dto = new NearBulletinDto();
		dto.setId(67);
		dto.setBulletinName("哈哈哈哈");
		dto.setBulletinPath("http://sdsdf");
		dto.setBulletinType(1);
		List<Integer> cityIds = new ArrayList<Integer>();
		cityIds.add(21760);
		dto.setCityIds(cityIds);
		service.updateNearBulletin(dto);
	}

	@Test
	public void queryNearBulletinTest() throws Exception {
		logNumberList(log, new TestNumberListAdapter() {
			
			@Override
			public void buildTestNumberList(Map<String, Object> jsonMap) throws Exception {
				NearBulletinDto dto = new NearBulletinDto();
//				dto.setCityId(19814);
				service.buildNearBulletin(dto, 1, 10, jsonMap);
			}
		});
		
	}
}
