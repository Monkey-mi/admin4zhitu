/**
 * 
 */
package com.imzhitu.admin.statistics.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.common.pojo.OpDataStatisticsDto;
import com.imzhitu.admin.constant.LoggerKeies;
import com.imzhitu.admin.op.dao.ChannelPVCacheDao;
import com.imzhitu.admin.statistics.mapper.DailyDataStatisticsMapper;
import com.imzhitu.admin.statistics.service.DailyDataStatisticsService;

/**
 * 数据统计实现类
 *
 * @author zhangbo 2015年6月19日
 */
public class DailyDataStatisticsServiceImpl extends BaseServiceImpl implements DailyDataStatisticsService {
	
	private static Logger log = Logger.getLogger(LoggerKeies.DAILY_DATA_STATISTICS);
	
	@Autowired
	private DailyDataStatisticsMapper dataMapper;
	
	@Autowired
	private ChannelPVCacheDao pvCacheDao;
	
	@Override
	public void queryDailyData(OpDataStatisticsDto dto, Integer page, Integer rows,
			Map<String, Object> jsonMap) throws Exception {
		dto.setFirstRow(page);
		dto.setLimit(rows);
		
		buildNumberDtos(dto,page,rows,jsonMap,new NumberDtoListAdapter<OpDataStatisticsDto>(){
			@Override
			public long queryTotal(OpDataStatisticsDto dto){
			    return dataMapper.queryDailyDataTotalCount(dto);
			}
			
			@Override
			public List< ? extends AbstractNumberDto> queryList(OpDataStatisticsDto dto){
				return dataMapper.queryDailyData(dto);
			}
			
		});
	}
	
	/**
	 * 数据统计定时执行方法
	 *
	 * @author zhangbo 2015年6月19日
	 * @throws Exception 
	 */
	@Override
	public void doDataStatisticsSchedulaJob(){
		// 由于是每天00执行定时方法，所以要获取的是前一天的数据，所以endTime设置为当前时间，startTime设置为前一天
		Date endTime = new Date();
		Date startTime = getBeforeDate(endTime);
		
		/** 输出格式: 2015-1-1 */
		String endTimeStr = DateFormat.getDateInstance(DateFormat.DEFAULT).format(endTime);
		String startTimeStr = DateFormat.getDateInstance(DateFormat.DEFAULT).format(startTime);
		// 做下转换，只取到日期，然后再转Date，得到就是当日00点
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			endTime = sdf.parse(endTimeStr);
			startTime = sdf.parse(startTimeStr);
		} catch (ParseException e) {
			log.error("日期转换错误：" + e.getMessage(), e);
		}
		
		// TODO 还要调用天杰提供的接口，获取每天的点击频道的次数，从缓存中获取
		Map<Integer, Integer> allPVMap = pvCacheDao.queryAllPV();
		
		// 获取前一天频道新增织图数，返回的dto中只有channelId与worldCount，可认为是键值对的列表
		List<OpDataStatisticsDto> worldCountList = dataMapper.queryDailyAddedChannelWorldCount(startTime, endTime);
		
		// 获取前一天新增频道订阅数，返回的dto中只有channelId与memberCount，可认为是键值对的列表
		List<OpDataStatisticsDto> memberCountList = dataMapper.queryDailyAddedChannelMemberCount(startTime.getTime(), endTime.getTime());
		
		// 获取前一天新增频道中织图的评论总数，返回的dto中只有channelId与commentCount，可认为是键值对的列表
		List<OpDataStatisticsDto> commentCountList = dataMapper.queryDailyAddedChannelWorldCommentCount(startTime, endTime);
		
		// 获取前一天新增频道中织图的点赞总数，返回的dto中只有channelId与likedCount，可认为是键值对的列表
		List<OpDataStatisticsDto> likedCountList = dataMapper.queryDailyAddedChannelWorldLikedCount(startTime, endTime);
		
		// 定义频道ID集合，要去重，得到一个最大的频道ID集合
		Set<Integer> channelIdSet = new HashSet<Integer>();
		// 把四个结果集中的频道ID取出，放入频道ID集合
		for (OpDataStatisticsDto dto : worldCountList) {
			channelIdSet.add(dto.getChannelId());
		}
		for (OpDataStatisticsDto dto : memberCountList) {
			channelIdSet.add(dto.getChannelId());
		}
		for (OpDataStatisticsDto dto : commentCountList) {
			channelIdSet.add(dto.getChannelId());
		}
		for (OpDataStatisticsDto dto : likedCountList) {
			channelIdSet.add(dto.getChannelId());
		}
		
		// 循环设置每日频道的相关数据
		for (Integer channelId : channelIdSet) {
			OpDataStatisticsDto saveDTO = new OpDataStatisticsDto();
			saveDTO.setChannelId(channelId);		// 设置频道ID
			saveDTO.setDataCollectDate(startTime);	// 设置收集数据日期，是前一天的数据
			saveDTO.setPvCount(allPVMap.get(channelId) == null ? 0:allPVMap.get(channelId));	// 设置频道每日PV数
			
			for (OpDataStatisticsDto worldCount : worldCountList) {
				if (worldCount.getChannelId().equals(channelId)) {
					saveDTO.setWorldAddCount(worldCount.getWorldAddCount());
					break;
				}
			}
			for (OpDataStatisticsDto memberCount : memberCountList) {
				if (memberCount.getChannelId().equals(channelId)) {
					saveDTO.setMemberAddCount(memberCount.getMemberAddCount());
					break;
				}
			}
			for (OpDataStatisticsDto commentCount : commentCountList) {
				if (commentCount.getChannelId().equals(channelId)) {
					saveDTO.setCommentAddCount(commentCount.getCommentAddCount());
					break;
				}
			}
			for (OpDataStatisticsDto likedCount : likedCountList) {
				if (likedCount.getChannelId().equals(channelId)) {
					saveDTO.setLikedAddCount(likedCount.getLikedAddCount());
					break;
				}
			}
			
			dataMapper.insertData(saveDTO);
		}
		
		// 每日执行完，则清空缓存
		pvCacheDao.clearAllPV();
	}
	
	/**
	 * 获取前一天日期
	 *
	 * @param now	当前时间
	 * @return
	 * @author zhangbo 2015年6月19日
	 */
	private Date getBeforeDate(Date now){
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(now);	//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
		dBefore = calendar.getTime();   //得到前一天的时间
		return dBefore;
	}
	
}
