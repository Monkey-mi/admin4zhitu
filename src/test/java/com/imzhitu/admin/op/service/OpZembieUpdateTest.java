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
public class OpZembieUpdateTest extends BaseTest {

	@Autowired
	private OpZombieService service;
	
	@Test
	public void testBuildRecommendUser() throws Exception {
		String ZombieInfo = "[{'userId':1597,'sex':2,'signature':'This is another things'}]";
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.updateSexAndSignature(ZombieInfo);
		logObj(jsonMap);
	}
}
