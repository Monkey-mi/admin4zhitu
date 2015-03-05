package com.imzhitu.admin.op.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.op.mapper.ChannelMapper;

public class ChannelMapperTest extends BaseTest {

	@Autowired
	private ChannelMapper mapper;
	
	@Test
	public void testUpdateValidByIds() {
		mapper.updateValidByIds(new Integer[]{1}, 1);
	}
}
