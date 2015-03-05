package com.imzhitu.admin.statistics.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.database.RowCallback;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.common.pojo.StatisticsCountGroupByDay;
import com.imzhitu.admin.common.pojo.StatisticsSummary;
import com.imzhitu.admin.statistics.dao.UserStatisticsDao;
import com.imzhitu.admin.statistics.dao.WorldStatisticsDao;
import com.imzhitu.admin.statistics.service.SummaryStatisticsService;

@Service
public class SummaryStatisticsServiceImpl extends BaseServiceImpl implements
		SummaryStatisticsService {

	private SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat fullTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private UserStatisticsDao userStatisticsDao;
	
	@Autowired
	private WorldStatisticsDao worldStatisticsDao;
	
	private Integer maxSumDay = 30;

	@Override
	public void buildSummary(Date beginDate, Date endDate, Map<String, Object> jsonMap) {
		if(beginDate == null || endDate == null) {
			endDate = new Date();
			Calendar ca = Calendar.getInstance();
			ca.setTime(endDate);
			ca.add(Calendar.DAY_OF_MONTH, -1);
			ca.set(Calendar.HOUR_OF_DAY, 0);
			ca.set(Calendar.MINUTE, 0);
			ca.set(Calendar.SECOND, 0);
			beginDate = ca.getTime();
		}
		
		final List<StatisticsSummary> summaryList = worldStatisticsDao.querySummaryGroupByDate(beginDate, endDate);
		int size = summaryList.size();
		if(size > 0) {
			final Map<String, Integer> indexMap = new HashMap<String, Integer>();
			String[] days = new String[size];
			for (int i = 0; i < size; i++) {
				String day = summaryList.get(i).getDate();
				days[i] = day;
				indexMap.put(day, i);
			}
			
			userStatisticsDao.queryRegisterCountGroupByDate(beginDate, endDate, new RowCallback<StatisticsSummary>() {
				
				@Override
				public void callback(StatisticsSummary t) {
					String day = t.getDate();
					Integer index = indexMap.get(day);
					if(index != null) {
						summaryList.get(index).setUserCount(t.getUserCount());
					}
				}
			});
		}
		jsonMap.put(OptResult.ROWS, summaryList);
		jsonMap.put(OptResult.TOTAL, size);
		
	}

	@Override
	public void buidlSummary(Date maxDate, int start, int limit,
			Map<String, Object> jsonMap) throws Exception {
		Date beginDate = null;
		Date endDate = null;
		int dayOffset = 0;
		Calendar ca = Calendar.getInstance();
		final Map<String, Integer> indexMap = new HashMap<String, Integer>();
		final List<StatisticsSummary> sumList = new ArrayList<StatisticsSummary>();
		
		if(start == 1) {
			maxDate = new Date();
		}
		ca.setTime(maxDate);

		if(start > 1) {
			dayOffset = -((start-1) * limit);
			ca.add(Calendar.DAY_OF_MONTH, dayOffset);
			ca.set(Calendar.HOUR_OF_DAY, 23);
			ca.set(Calendar.MINUTE, 59);
			ca.set(Calendar.SECOND, 59);
		}
		endDate = ca.getTime();
		
		ca.setTime(endDate);
		
		for(int i = 0; i < limit; i++) {
			ca.setTime(endDate);
			ca.add(Calendar.DAY_OF_MONTH, -i);
			String day = simpleFormat.format(ca.getTime());
			StatisticsSummary sum = new StatisticsSummary();
			sum.setDate(day);
			
			sumList.add(sum);
			indexMap.put(day, i);
		}
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		beginDate = ca.getTime();
		
		worldStatisticsDao.queryWorldCount(beginDate, endDate, 
				new RowCallback<StatisticsCountGroupByDay>() {

					@Override
					public void callback(StatisticsCountGroupByDay t) {
						String d = t.getDay();
						Integer i = indexMap.get(d);
						sumList.get(i).setWorldCount(t.getCount());
					}
			
		});
		
		worldStatisticsDao.queryChildWorldCount(beginDate, endDate, 
				new RowCallback<StatisticsCountGroupByDay>() {

					@Override
					public void callback(StatisticsCountGroupByDay t) {
						String d = t.getDay();
						Integer i = indexMap.get(d);
						sumList.get(i).setChildCount(t.getCount());
					}
			
		});
		
		userStatisticsDao.queryRegisterCount(beginDate, endDate, 
				new RowCallback<StatisticsCountGroupByDay>() {

					@Override
					public void callback(StatisticsCountGroupByDay t) {
						String d = t.getDay();
						Integer i = indexMap.get(d);
						sumList.get(i).setUserCount(t.getCount());
					}
		});
		
		jsonMap.put(OptResult.ROWS, sumList);
		jsonMap.put(OptResult.TOTAL, maxSumDay);
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, fullTimeFormat.format(maxDate));
	}

}
