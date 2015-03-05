package com.imzhitu.admin.op.dao;

import java.util.List;

import net.sf.json.JSONArray;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.common.pojo.OpChannelCover;
import com.hts.web.common.util.Log;
import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.op.mapper.ChannelCoverMapper;

public class ChannelCoverMapperTest extends BaseTest {

	@Autowired
	private ChannelCoverMapper mapper;
	
	@Test
	public void queryCacheThumbnail() throws Exception {
		Integer[] cids = new Integer[]{1,2,3};
		List<OpChannelCover> list = mapper.queryCacheCover(cids, 2);
		for(OpChannelCover cover : list) {
			Log.debug(cover.getChannelId() + " : " + cover.getTitleThumbPath());
		}
//		JSONArray jsArray = JSONArray.fromObject(list);
//		Log.debug(jsArray);
	}
}
