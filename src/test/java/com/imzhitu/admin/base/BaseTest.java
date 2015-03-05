package com.imzhitu.admin.base;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hts.web.common.util.Log;

/**
 * <p>
 * 测试基础类
 * </p>
 * 
 * 创建时间：2013-8-3
 * @author ztj
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring-config.xml"}) 
//@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)
//@Transactional
public abstract class BaseTest {
	
	protected Logger logger = Logger.getLogger(BaseTest.class);

	/**
	 * 将对象转换为JSON并logdebug输出
	 * @param obj
	 */
	protected static void logObj(Object obj) {
		JSONObject jsObj = JSONObject.fromObject(obj);
		Log.debug(jsObj);
	}
	
	protected void logNumberList(Logger logger, TestNumberListAdapter adapter) throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		adapter.buildTestNumberList(jsonMap);
		JSONObject jsObj = JSONObject.fromObject(jsonMap);
		logger.debug(jsObj);
	}
	
	protected interface TestNumberListAdapter {
		
		void buildTestNumberList(Map<String, Object> jsonMap) throws Exception;
		
	}
	
}
