package com.imzhitu.admin.op.service;


import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.Tag;
import com.imzhitu.admin.base.BaseTest;


public class OpChannelV2ServiceTest extends BaseTest {
	@Autowired
	private OpChannelV2Service service;
	
	@Test
	public void queryChannelLabelTest()throws Exception{
		service.queryOpChannelLabel("女神");
	}
	
	@Test
	public void queryChannelTest()throws Exception{
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		service.queryOpChannel(null,"人生",null, null, null, null, null, null,null, null, null, 1, 10, 0, jsonMap);
	}
	
	@Test
	public void insertChannelTest()throws Exception{
		service.insertOpChannel(4, "测试添加频道V2", "title", "subtitle", "description", "http://imzhitu.qiniudn.com/op/activity/1415436817000.png", "http://imzhitu.qiniudn.com/op/channel/1423107693000.png",1, "女神","1", 0, 0,0,0,0,Tag.FALSE,Tag.FALSE,0,Tag.FALSE,Tag.FALSE,Tag.TRUE,10001);
	}
	
	@Test
	public void updateChannelTest()throws Exception{
		service.updateOpChannel(86, 4, "test for update", "test for update title", "test subtitle", "test desc", null, null, 2, "test 女神", "1", 1, 2, 3, 4, 5, Tag.TRUE, Tag.TRUE, 1, Tag.TRUE, Tag.TRUE, Tag.FALSE,10002);
	}
	
	
}
