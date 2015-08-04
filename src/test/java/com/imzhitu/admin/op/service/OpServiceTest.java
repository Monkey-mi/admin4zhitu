package com.imzhitu.admin.op.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.OpActivityAward;
import com.hts.web.common.util.Log;
import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.OpActivityWorldDto;

/**
 * <p>
 * 织图运营子模块业务逻辑单元测试
 * </p>
 * 
 * 创建时间：2014-3-18
 * @author tianjie
 *
 */
public class OpServiceTest extends BaseTest{

	@Autowired
	private OpService service;
	
//	@Test
	public void testSaveActivity() throws Exception {
		Integer id = (int) (Math.random() * 1000000);
		service.saveActivity(
				id,
				"http://static.imzhitu.com/avatar/s/2014/02/05/15/23d241de6d2eb98675e4ee89c46f044c.jpg", 
				"http://static.imzhitu.com/avatar/s/2014/02/05/15/23d241de6d2eb98675e4ee89c46f044c.jpg", 
				"http://static.imzhitu.com/avatar/s/2014/02/05/15/23d241de6d2eb98675e4ee89c46f044c.jpg", 
				"测试","测试","测试",
				"http://www.imzhitu.com/testActivity",
				"http://static.imzhitu.com/avatar/s/2014/02/05/15/23d241de6d2eb98675e4ee89c46f044c.jpg",
				new Date(),
				new Date(),
				Tag.SQUARE_ACTIVITY_NORMAL, 
				"分享标题",
				"分享描述",
				"381,485",
				Tag.TRUE);
	}
	
//	@Test
	public void testUpdateActivity() throws Exception {
		service.updateActivity(
				34,
				"http://static.imzhitu.com/avatar/s/2014/02/05/15/23d241de6d2eb98675e4ee89c46f044c.jpg", 
				"http://static.imzhitu.com/avatar/s/2014/02/05/15/23d241de6d2eb98675e4ee89c46f044c.jpg", 
				"http://static.imzhitu.com/avatar/s/2014/02/05/15/23d241de6d2eb98675e4ee89c46f044c.jpg", 
				"测试","测试","测试",
				"http://www.imzhitu.com/testActivity",
				"http://static.imzhitu.com/avatar/s/2014/02/05/15/23d241de6d2eb98675e4ee89c46f044c.jpg",
				new Date(),
				new Date(),
				Tag.SQUARE_ACTIVITY_NORMAL, 
				"分享标题",
				"分享描述",
				"381,485",
				Tag.TRUE);
	}
	
	@Test
	public void testBuildActivityWorld() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildActivityWorld(6308, 7, null, null, null,null, null,null, 2, 10, jsonMap);
//		service.buildActivityWorld(0, 7, null, null, null,null,null, null, 1, 10, jsonMap);
		List<OpActivityWorldDto> list = (List<OpActivityWorldDto>) jsonMap.get(OptResult.ROWS);
//		logObj(jsonMap);
		for(OpActivityWorldDto dto : list) {
			Log.debug(dto.getSerial());
		}
	}
	
	@Test
	public void testBuildActivityLogo() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildActivityLogo(0, 1, 10, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testUpdateActivityWorldValid() throws Exception {
		service.updateActivityWorldValid(Tag.TRUE, 3222, 161, 12990, 485, "天天天杰", "您的织图通过活动审核#毕业季#");
	}
	
	@Test
	public void testUpdateActivityLogoValid() throws Exception {
		service.updateActivityLogoValid("1,2", Tag.TRUE);
	}
	
	@Test
	public void testQueryActivityById() throws Exception {
		service.queryActivityById(33);
	}
	
	@Test
	public void testSaveActivityAward() throws Exception {
		service.saveActivityAward(2, 
				"http://static.imzhitu.com/avatar/s/2014/02/05/15/23d241de6d2eb98675e4ee89c46f044c.jpg.thumbnail",
				"http://static.imzhitu.com/avatar/s/2014/02/05/15/23d241de6d2eb98675e4ee89c46f044c.jpg", 
				"名称",
				"描述",
				100.00d,
				"link",
				10,
				10);
	}
	
	@Test
	public void testBuildActivityAward() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildActivityAward(10, 1, 10, 2, jsonMap);
		service.buildActivityAward(0, 1, 10, 2, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testUpdateActivityAwardSerial() throws Exception {
		service.updateActivityAwardSerial(new String[]{"1"});
	}
	
	@Test
	public void testGetActivityAward() throws Exception {
		OpActivityAward award = service.getActivityAwardById(1);
		logObj(award);
	}
	
	@Test
	public void testUpdateActivityWorldValids() throws Exception {
		service.updateActivityWorldValids("759,758", Tag.TRUE);
	}
}

