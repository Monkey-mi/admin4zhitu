package com.imzhitu.admin.statistics.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.common.util.Log;
import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.StatisticsSummary;

public class WorldStatisticsDaoTest extends BaseTest {

	@Autowired
	private WorldStatisticsDao dao;
	
	@Test
	public void testQueryWorldCountGroupByDate() {
		Date endDate = new Date();
		Calendar ca = Calendar.getInstance();
		ca.setTime(endDate);
		ca.add(Calendar.DAY_OF_MONTH, -1);
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		Date beginDate = ca.getTime();
		List<StatisticsSummary> list = dao.querySummaryGroupByDate(beginDate, endDate);
		for(StatisticsSummary s : list) {
			Log.debug(s.getDate() + " : " + s.getWorldCount() + " : " +s.getChildCount());
		}
	}
}
