package com.imzhitu.admin.addr.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;
public class AddrServiceTest extends BaseTest{
	
	@Autowired
	private AddrService service;
	
	@Test
	public void getDistrictIdTest()throws Exception{
		Integer id = service.getDistrictId("新华区",19960);
		logger.info("----------------新华区id--------：---------"+id+"----------------------------------");
	}
	
	
	@Test
	public void getCityIdTest()throws Exception{
		Integer id = service.getCityId("深圳");
		logger.info("----------------深圳市id--------：---------"+id+"----------------------------------");
	}
	
	@Test
	public void getProvinceIdTest()throws Exception{
		Integer id = service.getProvinceId("山西");
		logger.info("----------------山西省id--------：---------"+id+"----------------------------------");
	}
	
	@Test
	public void updateCityCacheTest() throws Exception {
		service.updateCityCache();
	}
	
	@Test
	public void queryCityByIdsTest() throws Exception {
		service.queryCityByIds(new Integer[]{1,2});
	}
}
