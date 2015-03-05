package com.imzhitu.admin.interact.service;

import java.util.List;

import net.sf.json.JSONArray;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.Tag;
import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.InteractPlanComment;

public class InteractPlanCommentServiceTest extends BaseTest{
	@Autowired
	private InteractPlanCommentService service;
	
	@Test
	public void testaddPlanComment()throws Exception{
		service.addPlanComment(null,1, "just for test", Tag.TRUE, 14);
	}
	
	@Test
	public void testDelPlanCommentByIds()throws Exception{
		service.delPlanCommentByIds("1,2,3,4",14 );
	}

	@Test
	public void testQueryNRandomPlanCommentByGroupId()throws Exception{
		List<InteractPlanComment> list = service.queryNRandomPlanCommentByGroupId(4, 21);
		JSONArray jsArray = JSONArray.fromObject(list);
		logger.info(jsArray.toString());
	}
}
