/**
 * 
 */
package com.imzhitu.admin.statistics.service;

import java.text.ParseException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.statistics.service.DailyDataStatisticsService;
import com.imzhitu.admin.statistics.service.impl.DailyDataStatisticsServiceImpl;

/**
 *
 * @author zhangbo 2015年6月25日
 */
public class DailyDataStatisticsServiceImplTest  extends BaseTest{

	@Autowired
	private DailyDataStatisticsService service;
	
	/**
	 * Test method for {@link com.imzhitu.admin.statistics.service.impl.DailyDataStatisticsServiceImpl#doDataStatisticsSchedulaJob()}.
	 * @throws ParseException 
	 */
	@Test
	public void testDoDataStatisticsSchedulaJob() throws ParseException {
		service.doDataStatisticsSchedulaJob();
	}

}
