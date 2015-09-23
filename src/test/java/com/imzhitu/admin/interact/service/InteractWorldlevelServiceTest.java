package com.imzhitu.admin.interact.service;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.ZTWorldLevelDto;

public class InteractWorldlevelServiceTest extends BaseTest{
	
	@Autowired
	InteractWorldlevelService service;
	
	@Test
	public void testQueryWorldlevelList()throws Exception{
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		service.QueryWorldlevelList(100, 1, 10, jsonMap);
		JSONObject jsObj = JSONObject.fromObject(jsonMap);
		logger.debug(jsObj);
	}
	
	@Test
	public void testDeleteWorldlevelByIds()throws Exception{
		service.DeleteWorldlevelByIds("1,2");
	}
	
	@Test
	public void testAddWorldlevel()throws Exception{
		service.AddWorldlevel(new ZTWorldLevelDto(11612,1,2,10,20,10,20,10,20,10,"C",0));
	}
/*	@Test
	public void testAddLevelWorld()throws Exception{
		service.AddLevelWorld(11763,15, "39", "51,52");
	}
	*/
	@Test
	public void testQueryWorldLevel()throws Exception{
		service.QueryWorldLevel();
	}
	@Test
	public void testRandom(){
		Integer max = new Integer(20);
		Integer min = new Integer(10);
		int ma = max.intValue();
		int mi = min.intValue();
		double d;
		if(min>max||min<0)return ;
		int l=0;
		do{
			d = Math.random();
			l = (int)(Math.random()*10000000)%ma;
		}while(l<mi);
		logger.info("======================================>l is "+l);
	}
	
	@Test
	public void testAddTypeWorldInteract() throws Exception{
/*		service.AddTypeWorldInteract(11956,62,"");*/
	}
	
}
