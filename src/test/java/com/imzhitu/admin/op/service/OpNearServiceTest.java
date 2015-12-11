package com.imzhitu.admin.op.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.OpNearLabelDto;

public class OpNearServiceTest extends BaseTest{
	
	@Autowired
	private OpNearService nearService;
	
	private Logger log = Logger.getLogger(OpNearServiceTest.class);

	@Test
	public void queryNearLabelTest()throws Exception{
		Map<String,Object>jsonMap = new HashMap<String, Object>();
		OpNearLabelDto label = new OpNearLabelDto();
		nearService.queryNearLabel(label, 1, 10, jsonMap);
		log.info(jsonMap);
	}
	
	@Test
	public void updateNearLabelTest()throws Exception{
		OpNearLabelDto label = new OpNearLabelDto();
		label.setId(1);
		label.setCityId(21760);
		nearService.updateNearLabel(label);
	}
	
	@Test
	public void insertNearLabelTest()throws Exception{
		OpNearLabelDto label = new OpNearLabelDto();
		label.setId(1);
		label.setCityId(21760);
		label.setLabelName("标签");
		label.setDescription("");
		label.setBannerUrl("http://ww4.sinaimg.cn/large/a0a4bc09gw1er8n9yh6poj20fk078ab8.jpg");
		nearService.insertNearLabel(label);
	}
	
	@Test
	public void batchDeleteNearLabelTest()throws Exception{
		nearService.batchDeleteNearLabel("2");
	}
	
//	@Test
	public void insertNearCityGroupTest()throws Exception{
		nearService.insertNearCityGroup("测试附近城市组");
	}
	
	@Test
	public void batchDeleteNearCityGroupTest()throws Exception{
		nearService.batchDeleteNearCityGroup("3");
	}
	
	@Test
	public void queryNearCityGroupTest()throws Exception{
		Map<String,Object>jsonMap = new HashMap<String, Object>();
		nearService.queryNearCityGroup(null, 0, 1, 10, jsonMap);
		log.info(jsonMap);
	}
	
//	@Test
	public void insertNearRecommendCityTest()throws Exception{
		nearService.insertNearRecommendCity(19863, 1);
	}
	
	@Test
	public void batchDeleteNearRecommendCityTest()throws Exception{
		nearService.batchDeleteNearRecommendCity("4,5");
	}
	
	@Test
	public void queryNearRecommendCityTest()throws Exception{
		Map<String,Object>jsonMap = new HashMap<String, Object>();
		nearService.queryNearRecommendCity(null, null, 2, 0, 1, 10, jsonMap);
		log.info(jsonMap);
	}
	
//	@Test
	public void insertNearLabelWorldTest()throws Exception{
		nearService.insertNearLabelWorld(373547, 485, 1);
	}
	
	@Test
	public void batchDeleteNearLabelWorldTest()throws Exception{
		nearService.batchDeleteNearLabelWorld("3");
	}
	
	@Test
	public void queryNearLabelWorldTest()throws Exception{
		Map<String,Object>jsonMap = new HashMap<String, Object>();
		nearService.queryNearLabelWorld(null, null, 1, 0, 1, 10, jsonMap);
		log.info(jsonMap);
	}
	
	@Test
	public void queryNearWorldTest() throws Exception {
		Map<String,Object>jsonMap = new HashMap<String, Object>();
		nearService.queryNearWorld(21760, 0, 1, 10, jsonMap);
		log.info(jsonMap);
	}
}
