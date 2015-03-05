package com.imzhitu.admin.userinfo.service;

import java.util.List;

import net.sf.json.JSONArray;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.common.util.Log;
import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.UserSexLabelDto;

/**
 * <p>
 * 用户标签管理业务逻辑访问接口单元测试
 * </p>
 * 
 * 创建时间：2014-1-17
 * @author ztj
 *
 */
public class UserLabelServiceTest extends BaseTest {

	@Autowired
	private UserLabelService service;
	
	
	@Test
	public void testGetAllLabel() throws Exception {
		List<UserSexLabelDto> labels = service.getAllUserLabel();
		JSONArray jsArray = JSONArray.fromObject(labels);
		Log.debug(jsArray.toString());
	}
}
