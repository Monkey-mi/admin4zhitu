package com.imzhitu.admin.interact.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.constant.Emoji;

public class InteractParseResponseServiceTest extends BaseTest{
	
	@Autowired
	InteractParseResponseService service;
	
	@Test
	public void testParserQString()throws Exception{
		String result = service.parserQString(" @屁小囧\n\r\t : 深圳一般是\n幾時的哇？\n");
		logger.info(result);
		logger.info(result.trim().equals(Emoji.emojiTag));
	}

}
