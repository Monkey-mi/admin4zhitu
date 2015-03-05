package com.imzhitu.admin.ztworld.service;

import com.imzhitu.admin.base.BaseTest;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;
/**
 * 分类织图 单元测试
 * @author zxx
 *
 */
public class ZTWorldTypeWorldSchedulaServiceTest extends BaseTest{
	
	@Autowired
	private ZTWorldTypeWorldSchedulaService service;
	
	@Test
	public void addTypeWorldSchedulaTest()throws Exception{
		service.addTypeWorldSchedula(560,new Date(), 0,1);
	}
	@Test
	public void delTypeWorldSchedulaByIdsTest()throws Exception{
		service.delTypeWorldSchedulaByIds("549,560");
	}
	@Test
	public void queryTypeWorldSchedulaTest()throws Exception{
		Date now = new Date();
		Date begin = new Date(now.getTime() - 100*60*60*1000);
		Map<String ,Object> jsonMap = new HashMap<String,Object>();
		service.queryTypeWorldSchedula(null, 560,0,begin,now,1, 10, jsonMap);
		logger.info(jsonMap);
	}
	@Test
	public void getWorldIdBySchedulaTest()throws Exception{
		Date now = new Date();
		List<Integer> list = service.getWorldIdBySchedula(new Date(now.getTime()-40*60*1000), now);
		logger.info(list);
	}
}
