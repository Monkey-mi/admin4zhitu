package com.imzhitu.admin.interact.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.Tag;
import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.InteractPlanCommentLabel;

public class InteractPlanCommentLabelServiceTest extends BaseTest{
	@Autowired
	private InteractPlanCommentLabelService service;
	@Test
	public void testAddPlanCommentLabel()throws Exception{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startTime  = df.parse("2014-08-15");
		Date deadline  = df.parse("2015-08-15");
		service.addPlanCommentLabel(0, "测试标签", startTime, deadline, "1:00:00","16:00:00", 14, Tag.TRUE);
	}
	
	@Test
	public void testQueryPlancommentLabel() throws Exception {
		List<InteractPlanCommentLabel> list = service.queryInteractPlanCommentLabel();
		JSONArray jsArray = JSONArray.fromObject(list);
		logger.info(jsArray.toString());
	}
	
	@Test
	public void testqueryInteractPlanCommentLabelByDateAndTime()throws Exception{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");
		Date currentDate = new Date();
		List<InteractPlanCommentLabel> list = service.queryInteractPlanCommentLabelByDateAndTime(df.parse(df.format(currentDate)), df2.parse(df2.format(currentDate)));
		JSONArray jsArray = JSONArray.fromObject(list);
		logger.info(jsArray.toString());
	}
}
