package com.imzhitu.admin.op.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

/**
 * <p>
 * 用户运营子模块业务逻辑单元测试
 * </p>
 * 
 * 创建时间：2014-3-18
 * @author tianjie
 *
 */
public class OpUserServiceTest extends BaseTest {

	@Autowired
	private OpUserService service;
	
	@Test
	public void testBuildRecommendUser() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildRecommendUser(0, 1, 10, null, null, null,null, null, null,1, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testQueryZombie() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Date now = new Date();
		service.buildZombieUser(0,100, null,null, new Date(now.getTime() - 390L*24*60*60000), now, 1,10, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testDelStar()throws Exception {
		service.delStar(123);
	}
	
	@Test
	public void testGetRandomUnFollowUserZombieIds()throws Exception{
		List<Integer> list =service.getRandomUnFollowUserZombieIds(114,1723, 20);
		logger.info(list);
	}
	
	@Test
	public void testGetRandomFollowUser()throws Exception{
		List<Integer> list = service.getRandomFollowUserZombieIds(527, 1372, 1, 1);
		logger.info(list);
	}
	
	@Test
	public void testUserAcceptRecommendDirect()throws Exception{
		service.userAcceptRecommendDirect(1595, 1);
	}
	
	@Test
	public void updateZombieSexTest()throws Exception{
		service.updateZombieSex(1, 114);
	}
	
	@Test
	public void updateZombieUserName()throws Exception{
		service.updateZombieUserName("小织图", 114);
	}
	
	@Test
	public void updateZombieSignture() throws Exception{
		service.updateZombieSignText("你是我的小呀小织图", 114);
	}
	
	@Test
	public void insertDelMessageTest()throws Exception{
		service.insertDelMessage(1595, true);
	}
}
