package com.imzhitu.admin.op.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.channel.service.ChannelWorldService;
import com.imzhitu.admin.common.pojo.OpChannelV2Dto;
import com.imzhitu.admin.common.pojo.OpChannelWorld;
import com.imzhitu.admin.common.pojo.OpChannelWorldDto;
import com.imzhitu.admin.common.pojo.OpChannelWorldSchedulaDto;
import com.imzhitu.admin.common.util.AdminLoginUtil;
import com.imzhitu.admin.op.mapper.ChannelWorldMapper;
import com.imzhitu.admin.op.mapper.OpChannelV2Mapper;
import com.imzhitu.admin.op.mapper.OpChannelWorldSuperbSchedulaMapper;
import com.imzhitu.admin.op.mapper.OpChannelWorldValidSchedulaMapper;
import com.imzhitu.admin.op.service.ChannelService;
import com.imzhitu.admin.op.service.OpChannelWorldSchedulaService;

//@Service
public class OpChannelWorldSchedulaServiceImpl extends BaseServiceImpl implements OpChannelWorldSchedulaService{
	public static Integer workingTime=60*60*1000;
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private com.hts.web.operations.service.ChannelService webCannelService;
	
	@Autowired
	private OpChannelWorldValidSchedulaMapper channelWorldValidSchedulaMapper;
	
	@Autowired
	private OpChannelWorldSuperbSchedulaMapper channelWorldSuperbSchedulaMapper;
	
	@Autowired
	private OpChannelV2Mapper channelMapper;
	
	@Autowired
	private ChannelWorldMapper channelWorldMapper;
	
	@Autowired
	private ChannelWorldService channelWorldService;
	
	@Value("${urlPrefix}")
	private String urlPrefix;
	public String getUrlPrefix() {
		return urlPrefix;
	}

	public void setUrlPrefix(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}
	
	Logger logger = Logger.getLogger(OpChannelWorldSchedulaServiceImpl.class);


	/**
	 * 分页查询频道织图计划
	 * @throws Exception
	 */
	@Override
	public void queryChannelWorldValidSchedulaForList(Integer maxId, int page, int rows, 
			Integer id, Integer userId, Integer worldId,Integer channelId,Integer finish,
			Integer valid,Date addDate,Date modifyDate, Map<String, Object> jsonMap)throws Exception{
		OpChannelWorldSchedulaDto dto = new OpChannelWorldSchedulaDto();
		dto.setAddDate(addDate);
		dto.setChannelId(channelId);
		dto.setFinish(finish);
		dto.setModifyDate(modifyDate);
		dto.setId(id);
		dto.setUserId(userId);
		dto.setWorldId(worldId);
		buildNumberDtos(dto,page,rows,jsonMap,new NumberDtoListAdapter<OpChannelWorldSchedulaDto>(){
			@Override
			public long queryTotal(OpChannelWorldSchedulaDto dto){
				return channelWorldValidSchedulaMapper.queryChannelWorldValidSchedulaCount(dto);
			}
			
			@Override
			public List< ? extends AbstractNumberDto> queryList(OpChannelWorldSchedulaDto dto){
				List<OpChannelWorldSchedulaDto> list = channelWorldValidSchedulaMapper.queryChannelWorldValidSchedulaForList(dto);
				for(OpChannelWorldSchedulaDto o:list){
					o.setWorldLink(urlPrefix + o.getWorldLink());
				}
				return list;
			}
			
		});
	}
	
	/**
	 * mishengliang
	 * 分页查询频道精选计划
	 * @throws Exception
	 */
	@Override
	public void queryChannelWorldSuperbSchedulaForList(Integer maxId, int page, int rows, 
			Integer id, Integer userId, Integer worldId,Integer channelId,Integer finish,
			Integer valid,Date addDate,Date modifyDate, Map<String, Object> jsonMap)throws Exception{
		OpChannelWorldSchedulaDto dto = new OpChannelWorldSchedulaDto();
		dto.setAddDate(addDate);
		dto.setChannelId(channelId);
		dto.setFinish(finish);
		dto.setModifyDate(modifyDate);
		dto.setId(id);
		dto.setUserId(userId);
		dto.setWorldId(worldId);
		buildNumberDtos(dto,page,rows,jsonMap,new NumberDtoListAdapter<OpChannelWorldSchedulaDto>(){
			@Override
			public long queryTotal(OpChannelWorldSchedulaDto dto){
				return channelWorldSuperbSchedulaMapper.queryChannelWorldSuperbSchedulaCount(dto);
			}
			
			@Override
			public List< ? extends AbstractNumberDto> queryList(OpChannelWorldSchedulaDto dto){
				List<OpChannelWorldSchedulaDto> list = channelWorldSuperbSchedulaMapper.queryChannelWorldSuperbSchedulaForList(dto);
				for(OpChannelWorldSchedulaDto o:list){
					o.setWorldLink(urlPrefix + o.getWorldLink());
				}
				return list;
			}
			
		});
	}
	
	@Override
	public List<OpChannelWorldSchedulaDto> queryChannelWorldValidSchedulaForList(Integer channelId, Integer worldId) throws Exception {
		return channelWorldValidSchedulaMapper.queryChannelWorldValidSchedulaListByChannelIdAndWorldId(channelId, worldId);
	}
	
	/**
	 * 批量删除频道有效计划
	 * @param idsStr
	 * @throws Exception
	 */
	@Override
	public void delChannelWorldValidSchedula(String idsStr)throws Exception{
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		channelWorldValidSchedulaMapper.delChannelWorldValidSchedula(ids);
	}
	
	
	/**
	 * 批量删除频道精选计划
	 * @param idsStr
	 * @throws Exception
	 */
	@Override
	public void delChannelWorldSuperbSchedula(String idsStr)throws Exception{
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		channelWorldSuperbSchedulaMapper.delChannelWorldSuperbSchedula(ids);
	}
	
	/**
	 * 批量添加
	 */
	public void batchChannelWorldToSortAndValidSchedula(Integer channelId, Integer[] worldIds, Date schedula, Integer minuteTimeSpan)throws Exception{
		// 获取当前登陆的管理员账号id
		Integer operatorId = AdminLoginUtil.getCurrentLoginId();
		Date now = new Date();
		long timeSpan = minuteTimeSpan*60*1000L;
		for(int i=0;i<worldIds.length; i++){
			Integer worldId = worldIds[i];
			
			// 查询频道织图，list查出来应该只有一个对象
			OpChannelWorldSchedulaDto dto = new OpChannelWorldSchedulaDto();			
			dto.setWorldId(worldId);
			dto.setChannelId(channelId);
			long r = channelWorldValidSchedulaMapper.queryChannelWorldValidSchedulaCount(dto);
			
			dto.setModifyDate(now);
			dto.setAddDate(now);
			dto.setSchedulaDate(new Date(schedula.getTime()+timeSpan*i));
			dto.setFinish(Tag.FALSE);
			dto.setOperatorId(operatorId);
			
			if(0 == r){
				channelWorldValidSchedulaMapper.insertChannelWorldValidSchedula(dto);
			}else{
				channelWorldValidSchedulaMapper.updateChannelWorldValidSchedula(dto);
			}
		}
	}
	
	@Override
	public void batchChannelWorldToSuperbSchedula(Integer channelId, Integer[] worldIds, Date schedula, Integer minuteTimeSpan) {
		// 获取当前登陆的管理员账号id
		Integer operatorId = AdminLoginUtil.getCurrentLoginId();
		Date now = new Date();
		
		// 根据时间间隔转化为毫秒
		long timeSpan = minuteTimeSpan * 60 * 1000L;
		
		for(int i=0;i<worldIds.length; i++){
			Integer worldId = worldIds[i];
			
			// 查询频道织图，list查出来应该只有一个对象
			OpChannelWorldSchedulaDto dto = new OpChannelWorldSchedulaDto();		
			dto.setWorldId(worldId);
			dto.setChannelId(channelId);
			long r = channelWorldSuperbSchedulaMapper.queryChannelWorldSuperbSchedulaCount(dto);
			
			dto.setModifyDate(now);
			dto.setAddDate(now);
			dto.setSchedulaDate(new Date(schedula.getTime() + timeSpan * i));
			dto.setOperatorId(operatorId);
			dto.setFinish(Tag.FALSE);
			
			if(0 == r){
				channelWorldSuperbSchedulaMapper.insertChannelWorldSuperbSchedula(dto);
			}else{
				channelWorldSuperbSchedulaMapper.updateChannelWorldSuperbSchedula(dto);
			}
		}
	}
	
	public void channelWorldSchedula() {
		Date begin = new Date();
		logger.info("channelWorld Schedula Begin:" + begin.toString());
		
		Date endTime = new Date();
		Date beginTime = new Date(endTime.getTime() - workingTime);
		
		try {
			channelWorldValidSchedula(beginTime, endTime);
			
			channelWorldSuperbSchedula(beginTime, endTime);
		} catch (Exception e) {
			logger.error("execute the ChannelWorld Valid Or Superb Schedule fail", e);
		}
		
		Date end = new Date();
		logger.info("channelWorld Schedula End:" + end.toString() + ". Cost: " + (end.getTime() - begin.getTime()) + "ms.");
	}
	
	/**
	 * 频道织图计划生效
	 * 
	 * @param beginTime	查询时间段起始
	 * @param endTime	查询时间段结束
	 * @throws Exception
	 * @author zhangbo	2015年9月14日
	 */
	private void channelWorldValidSchedula(Date beginTime, Date endTime) throws Exception {
		OpChannelWorldSchedulaDto queryDto = new OpChannelWorldSchedulaDto();
		queryDto.setAddDate(beginTime);
		queryDto.setModifyDate(endTime);
		queryDto.setFinish(Tag.FALSE);
		// 获取出的集合是按照调度时间的正序来排列的，如：第一个数据为10:00，最后一个数据为10:50
		List<OpChannelWorldSchedulaDto> list = channelWorldValidSchedulaMapper.queryChannelWorldValidSchedulaForList(queryDto);
		
		for (OpChannelWorldSchedulaDto schedulaDto : list) {
		    /*
		     * 优先执行调度表的刷新，若下列更新频道表失败，则人为操作，重新设置有效性与加精
		     * 因为考虑到若频道表刷新执行失败，那么调度表不会被置为成功，每次都会执行，会加大服务器压力
		     */
		    OpChannelWorldSchedulaDto updateSchedulaDto = new OpChannelWorldSchedulaDto();
		    updateSchedulaDto.setModifyDate(new Date());
		    updateSchedulaDto.setId(schedulaDto.getId());
		    updateSchedulaDto.setFinish(Tag.TRUE);
		    channelWorldValidSchedulaMapper.updateChannelWorldValidSchedula(updateSchedulaDto);
		    
		    // 执行频道织图生效
		    channelWorldService.setChannelWorldValidByScheduler(schedulaDto.getChannelId(), schedulaDto.getWorldId());
		}
	}
	
	/**
	 * 频道织图计划加精
	 * 
	 * @param beginTime	查询时间段起始
	 * @param endTime	查询时间段结束
	 * @throws Exception
	 * @author zhangbo	2015年9月14日
	 */
	private void channelWorldSuperbSchedula(Date beginTime, Date endTime) throws Exception {
		OpChannelWorldSchedulaDto queryDto = new OpChannelWorldSchedulaDto();
		queryDto.setAddDate(beginTime);
		queryDto.setModifyDate(endTime);
		queryDto.setFinish(Tag.FALSE);
		// 获取出的集合是按照调度时间的正序来排列的，如：第一个数据为10:00，最后一个数据为10:50
		List<OpChannelWorldSchedulaDto> list = channelWorldSuperbSchedulaMapper.queryChannelWorldSuperbSchedulaForList(queryDto);
		
		for (OpChannelWorldSchedulaDto schedulaDto : list) {
		    
		    /*
		     * 优先执行调度表的刷新，若下列更新频道表失败，则人为操作，重新设置有效性与加精
		     * 因为考虑到若频道表刷新执行失败，那么调度表不会被置为成功，每次都会执行，会加大服务器压力
		     */
		    OpChannelWorldSchedulaDto updateSchedulaDto = new OpChannelWorldSchedulaDto();
		    updateSchedulaDto.setModifyDate(new Date());
		    updateSchedulaDto.setId(schedulaDto.getId());
		    updateSchedulaDto.setFinish(Tag.TRUE);
		    channelWorldSuperbSchedulaMapper.updateChannelWorldSuperbSchedula(updateSchedulaDto);
		    
		    channelService.updateChannelWorldSuperb(schedulaDto.getChannelId(), schedulaDto.getWorldId(), Tag.TRUE);
		    
		    channelService.updateChannelWorldSuperbSerial(schedulaDto.getChannelId(), schedulaDto.getWorldId());
		    
		    // 更新精选总数, 加精和取消加精调用
		    webCannelService.updateSuperbCount(schedulaDto.getChannelId());
		}
	}
	
	/**
	 * 重新排序
	 * @param wIds
	 * @param schedula
	 * @param operator
	 * @throws Exception
	 */
	@Override
	public void reSortValid(String[] ids,Date schedula,Integer minuteTimeSpan,Integer operator)throws Exception{
		long timeSpan = minuteTimeSpan*60*1000L;
		Date now = new Date();
		for(int i=  0;i < ids.length; i++){
			String idStr = ids[i];
			if(idStr != null && idStr != ""){
				OpChannelWorldSchedulaDto dto = new OpChannelWorldSchedulaDto();
				int id = Integer.parseInt(ids[i]);
				long t = schedula.getTime() + i*timeSpan;//用以排序
				dto.setModifyDate(now);
				dto.setId(id);
				dto.setOperatorId(operator);
				dto.setSchedulaDate(new Date(t));
				channelWorldValidSchedulaMapper.updateChannelWorldValidSchedula(dto);
			}
		}
	}
	
	@Override
	public void reSortSuperb(String[] ids,Date schedula,Integer minuteTimeSpan,Integer operator)throws Exception{
		long timeSpan = minuteTimeSpan*60*1000L;
		Date now = new Date();
		for(int i=  0;i < ids.length; i++){
			String idStr = ids[i];
			if(idStr != null && idStr != ""){
				OpChannelWorldSchedulaDto dto = new OpChannelWorldSchedulaDto();
				int id = Integer.parseInt(ids[i]);
				long t = schedula.getTime() + i*timeSpan;//用以排序
				dto.setModifyDate(now);
				dto.setId(id);
				dto.setOperatorId(operator);
				dto.setSchedulaDate(new Date(t));
				channelWorldSuperbSchedulaMapper.updateChannelWorldSuperbSchedula(dto);
			}
		}
	}
	
	/**
	 * 定时随机频道织图精选前5个织图
	 * 
	 * @author zhangbo	2015年9月16日
	 */
	public void randomChannelWorldSuperbTop() {
		OpChannelV2Dto queryDto = new OpChannelV2Dto();
		queryDto.setValid(Tag.TRUE);
		List<OpChannelV2Dto> channelList = channelMapper.queryOpChannel(queryDto);
		for (OpChannelV2Dto channelDto : channelList) {
			// 获取频道加精织图的最新5个
			OpChannelWorld queryChannelWorldDto = new OpChannelWorld();
			queryChannelWorldDto.setChannelId(channelDto.getChannelId());
			queryChannelWorldDto.setValid(Tag.TRUE);
			queryChannelWorldDto.setSuperb(Tag.TRUE);
			queryChannelWorldDto.setFirstRow(0);
			queryChannelWorldDto.setLimit(5);
			List<OpChannelWorldDto> channelWorldSuperbTop5List = channelWorldMapper.queryChannelWorlds(queryChannelWorldDto);
			
			// 随机打乱频道加精织图前5个集合
			Collections.shuffle(channelWorldSuperbTop5List);
			
			for (OpChannelWorldDto channelWorldSuperb : channelWorldSuperbTop5List) {
				// 刷新频道织图的精选排序，即superb_serial，因为已经乱序了，所以就直接刷新就可以
			    try {
					channelService.updateChannelWorldSuperbSerial(channelWorldSuperb.getChannelId(), channelWorldSuperb.getWorldId());
				} catch (Exception e) {
					logger.error("Refresh ChannelWorld Superb Serial as superb_serial Fail:", e);
				}
			}
		}
	}

}
