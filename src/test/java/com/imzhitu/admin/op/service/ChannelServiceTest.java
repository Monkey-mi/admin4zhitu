package com.imzhitu.admin.op.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.Tag;
import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.OpChannel;
import com.imzhitu.admin.common.pojo.OpChannelTopOne;
import com.imzhitu.admin.common.pojo.OpChannelTopOnePeriod;
import com.imzhitu.admin.common.pojo.OpChannelTopType;
import com.imzhitu.admin.common.pojo.OpChannelWorld;

public class ChannelServiceTest extends BaseTest {

	public Logger logger = Logger.getLogger(ChannelServiceTest.class);
	
	@Autowired
	private ChannelService service;
	
	@Test
	public void testUpdateChannelTopOneCache() throws Exception {
		service.updateTopOneCache();
	}
	
	@Test
	public void testUpdateChannelTopOneTitleCache() throws Exception {
		service.updateTopOneTitleCache(new Date(), new Date());
	}
	
	@Test
	public void testBuildChannel() throws Exception {
		logNumberList(logger, new TestNumberListAdapter(){

			@Override
			public void buildTestNumberList(Map<String, Object> jsonMap)
					throws Exception {
				OpChannel channel = new OpChannel();
				service.buildChannel(channel, 1, 10, jsonMap);
				channel.setMaxId(3);
				service.buildChannel(channel, 1, 10, jsonMap);
			}
			
		});
	}
	
	@Test
	public void testQueryChannelById() throws Exception {
		service.queryChannelById(1);
	}
	
	@Test
	public void testUpdateChannel() throws Exception {
		OpChannel channel = new OpChannel();
		channel.setId(4);
		int rand = (int)(Math.random() * 10000);
		channel.setChannelName("测试频道" + rand); 
		channel.setValid(Tag.TRUE);
		channel.setChannelTitle("测试频道标题" + rand);
		channel.setChannelIcon("http://static.imzhitu.com/op%2Fchannel%2F%E5%90%83%E8%B4%A7_%402x.png");
		service.updateChannel(channel);;
	}
	
	@Test
	public void testDeleteChannel() throws Exception {
		service.deleteChannel("4,5");
	}
	
	@Test
	public void testAddChannelSerial() throws Exception {
		service.addChannelSerial(new String[]{"2", "1"});
	}
	
	@Test
	public void testBuildTopOne() throws Exception {
		logNumberList(logger, new TestNumberListAdapter(){

			@Override
			public void buildTestNumberList(Map<String, Object> jsonMap)
					throws Exception {
				OpChannelTopOne topOne = new OpChannelTopOne();
				service.buildTopOneDto(topOne, 1, 10, jsonMap);
				topOne.setMaxId(10);
				service.buildTopOneDto(topOne, 1, 10, jsonMap);
			}
		});
	}
	
	@Test
	public void testSaveTopOne() throws Exception {
		OpChannelTopOne topOne = new OpChannelTopOne();
		topOne.setTopId(1);
		topOne.setUserId(485);
		topOne.setPeriod(1);
		topOne.setValid(Tag.TRUE);
		topOne.setNotified(Tag.FALSE);
		service.saveTopOne(topOne);
	}
	
	@Test
	public void testUpdateTopOne() throws Exception {
		OpChannelTopOne topOne = new OpChannelTopOne();
		topOne.setId(1);
		topOne.setTopId(2);
		topOne.setUserId(485);
		topOne.setPeriod(1);
		topOne.setValid(Tag.TRUE);
		topOne.setNotified(Tag.FALSE);
		service.updateTopOne(topOne);
	}
	
	@Test
	public void testUpdateTopOneValid() throws Exception {
		service.updateTopOneValid("12,13", Tag.TRUE);
	}
	
	@Test
	public void testDeleteTopOne() throws Exception {
		service.deleteTopOne("12,13");
	}

	@Test
	public void testBuildTopType() throws Exception {
		logNumberList(logger, new TestNumberListAdapter(){

			@Override
			public void buildTestNumberList(Map<String, Object> jsonMap)
					throws Exception {
				service.queryTopType(true);
			}
		});
	}
	
	@Test
	public void testSaveTopType() throws Exception {
		OpChannelTopType topType = new OpChannelTopType();
		topType.setTopTitle("女人最多");
		topType.setTopSubTitle("呵呵");
		service.saveTopType(topType);
	}
	
	@Test
	public void testUpdateTopType() throws Exception {
		OpChannelTopType topType = new OpChannelTopType();
		topType.setId(100);
		topType.setTopTitle("女人最多");
		topType.setTopSubTitle("呵呵");
		service.updateTopType(topType);
	}
	
	@Test
	public void testDeleteTopType() throws Exception {
		service.deleteTopTypes("5,6");
	}
	
	@Test
	public void testBuildChannelWorld() throws Exception {
		logNumberList(logger, new TestNumberListAdapter(){

			@Override
			public void buildTestNumberList(Map<String, Object> jsonMap)
					throws Exception {
				OpChannelWorld world = new OpChannelWorld();
				world.setChannelId(1);
				world.setMaxId(10);
				service.buildChannelWorld(world, null, 1, 10, jsonMap);
				world.setMaxId(null);
				service.buildChannelWorld(world, null, 1, 10, jsonMap);
			}
		});
	}
	
//	@Test
	public void testSaveChannelWorld() throws Exception {
		OpChannelWorld world = new OpChannelWorld();
		world.setChannelId(1);
		int rand = (int)(Math.random() * 1000000);
		world.setWorldId(14932 + rand);
		world.setDateAdded(new Date());
		world.setAuthorId(485);
		world.setValid(Tag.TRUE);
		world.setNotified(Tag.TRUE);
		service.saveChannelWorld(world);
	}
	
	@Test
	public void testUpdateChannelWorld() throws Exception {
		OpChannelWorld world = new OpChannelWorld();
		world.setChannelId(1);
		world.setValid(Tag.TRUE);
		world.setNotified(Tag.TRUE);
		world.setId(12);
		service.updateChannelWorld(world);
	}
	
	@Test
	public void testDeleteChannelWorld() throws Exception {
		service.deleteChannelWorlds("12");
	}
	
	@Test
	public void testAddChannelWorldId() throws Exception {
		service.addChannelWorldId(1, new String[]{"12"});
		service.addChannelWorldId(1, new Integer[]{12});
	}
	
	@Test
	public void testAddTopOneRecommendMsg() throws Exception {
		service.addTopOneRecommendMsg(11);
	}
	
	@Test
	public void queryLabelTopOneTest()throws Exception{
		OpChannelTopOne dto = new OpChannelTopOne();
		Date endDate = new Date();
		Date beginDate = new Date(endDate.getTime()-3*24*60*60*1000);
		
		dto.setBeginDate(beginDate);
		dto.setEndDate(endDate);
		List<Integer> userId = service.queryLabelTopOne(dto);
		logger.info("===============labelTopOne is:"+ userId);
	}
	
	@Test
	public void queryBeLikedTopOneTest()throws Exception{
		OpChannelTopOne dto = new OpChannelTopOne();
		Date endDate = new Date();
		Date beginDate = new Date(endDate.getTime()-3*24*60*60*1000);
		
		dto.setBeginDate(beginDate);
		dto.setEndDate(endDate);
		List<Integer> userId = service.queryBeLikedTopOne(dto);
		logger.info("===============BeLikedTopOne is:"+ userId);
	}
	
	
	@Test
	public void queryCommentTopOneTest()throws Exception{
		OpChannelTopOne dto = new OpChannelTopOne();
		Date endDate = new Date();
		Date beginDate = new Date(endDate.getTime()-3*24*60*60*1000);
		
		dto.setBeginDate(beginDate);
		dto.setEndDate(endDate);
		List<Integer> userId = service.queryCommentTopOne(dto);
		logger.info("===============queryCommentTopOne is:"+ userId);
	}
	
	@Test
	public void queryFollowerIncreaseTopOneTest()throws Exception{
		OpChannelTopOne dto = new OpChannelTopOne();
		Date endDate = new Date();
		Date beginDate = new Date(endDate.getTime()-3*24*60*60*1000);
		
		dto.setBeginDate(beginDate);
		dto.setEndDate(endDate);
		List<Integer> userId = service.queryFollowerIncreaseTopOne(dto);
		logger.info("===============queryFollowerIncreaseTopOne is:"+ userId);
	}
	
	@Test
	public void queryFollowTopOneTest()throws Exception{
		OpChannelTopOne dto = new OpChannelTopOne();
		Date endDate = new Date();
		Date beginDate = new Date(endDate.getTime()-3*24*60*60*1000);
		
		dto.setBeginDate(beginDate);
		dto.setEndDate(endDate);
		List<Integer> userId = service.queryFollowTopOne(dto);
		logger.info("===============queryFollowTopOne is:"+ userId);
	}
	
	@Test
	public void querylikeTopOneTest()throws Exception{
		OpChannelTopOne dto = new OpChannelTopOne();
		Date endDate = new Date();
		Date beginDate = new Date(endDate.getTime()-3*24*60*60*1000);
		
		dto.setBeginDate(beginDate);
		dto.setEndDate(endDate);
		List<Integer> userId = service.querylikeTopOne(dto);
		logger.info("===============querylikeTopOne is:"+ userId);
	}
	
	@Test
	public void querySuperbTopOneTest()throws Exception{
		OpChannelTopOne dto = new OpChannelTopOne();
		Date endDate = new Date();
		Date beginDate = new Date(endDate.getTime()-3*24*60*60*1000);
		
		dto.setBeginDate(beginDate);
		dto.setEndDate(endDate);
		List<Integer> userId = service.querySuperbTopOne(dto);
		logger.info("===============querySuperbTopOne is:"+ userId);
	}
	
	@Test
	public void queryBeClickTopOneTest()throws Exception{
		OpChannelTopOne dto = new OpChannelTopOne();
		Date endDate = new Date();
		Date beginDate = new Date(endDate.getTime()-3*24*60*60*1000);
		
		dto.setBeginDate(beginDate);
		dto.setEndDate(endDate);
		List<Integer> userId = service.queryBeClickTopOne(dto);
		logger.info("===============queryBeClickTopOne is:"+ userId);
	}
	
	
	@Test
	public void queryWorldTopOneTest()throws Exception{
		OpChannelTopOne dto = new OpChannelTopOne();
		Date endDate = new Date();
		Date beginDate = new Date(endDate.getTime()-3*24*60*60*1000);
		
		dto.setBeginDate(beginDate);
		dto.setEndDate(endDate);
		List<Integer> userId = service.queryWorldTopOne(dto);
		logger.info("===============queryWorldTopOne is:"+ userId);
	}
	
	
	@Test
	public void queryPictureTopOneTest()throws Exception{
		OpChannelTopOne dto = new OpChannelTopOne();
		Date endDate = new Date();
		Date beginDate = new Date(endDate.getTime()-3*24*60*60*1000);
		
		dto.setBeginDate(beginDate);
		dto.setEndDate(endDate);
		List<Integer> userId = service.queryPictureTopOne(dto);
		logger.info("===============queryPictureTopOne is:"+ userId);
	}
	
	@Test
	public void queryActivityTopOneTest()throws Exception{
		OpChannelTopOne dto = new OpChannelTopOne();
		Date endDate = new Date();
		Date beginDate = new Date(endDate.getTime()-3*24*60*60*1000);
		
		dto.setBeginDate(beginDate);
		dto.setEndDate(endDate);
		List<Integer> userId = service.queryActivityTopOne(dto);
		logger.info("===============queryActivityTopOne is:"+ userId);
	}
	
	@Test
	public void updateTopOneTest()throws Exception {
		service.updateTopOne();
	}
	
	@Test
	public void testQueryTopOnePeriodList() throws Exception {
		OpChannelTopOnePeriod p = new OpChannelTopOnePeriod();
		p.setFirstRow(0);
		p.setLimit(10);
		service.queryTopOnePeriodList(p, 1, 10, true);
	}
	
	@Test
	public void updateChannelWorldValidTest() throws Exception {
//		service.updateChannelWorldValid(11832,373502, 1);
	}

	@Test
	public void updateChannelWorldSuperbTest() throws Exception {
//		service.updateChannelWorldSuperb(11823, 27787, 1,"_superb");
	}
	
}
