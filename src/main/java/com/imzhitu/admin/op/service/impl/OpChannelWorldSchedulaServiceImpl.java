package com.imzhitu.admin.op.service.impl;

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
import com.imzhitu.admin.common.pojo.OpChannelWorld;
import com.imzhitu.admin.common.pojo.OpChannelWorldSchedulaDto;
import com.imzhitu.admin.op.mapper.OpChannelWorldSchedulaMapper;
import com.imzhitu.admin.op.service.ChannelService;
import com.imzhitu.admin.op.service.OpChannelWorldSchedulaService;

//@Service
public class OpChannelWorldSchedulaServiceImpl extends BaseServiceImpl implements OpChannelWorldSchedulaService{
	public static Integer workingTime=60*60*1000;
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private OpChannelWorldSchedulaMapper channelWorldSchedulaMapper;
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
	 * 分页查询
	 * @throws Exception
	 */
	@Override
	public void queryChannelWorldSchedulaForList(Integer maxId, int page, int rows, 
			Integer id, Integer userId, Integer worldId,Integer channelId,Integer finish,
			Integer valid,Date addDate,Date modifyDate, Map<String, Object> jsonMap)throws Exception{
		OpChannelWorldSchedulaDto dto = new OpChannelWorldSchedulaDto();
		dto.setAddDate(addDate);
		dto.setChannelId(channelId);
		dto.setFinish(finish);
		dto.setModifyDate(modifyDate);
		dto.setId(id);
		dto.setValid(valid);
		dto.setUserId(userId);
		dto.setWorldId(worldId);
		buildNumberDtos(dto,page,rows,jsonMap,new NumberDtoListAdapter<OpChannelWorldSchedulaDto>(){
			@Override
			public long queryTotal(OpChannelWorldSchedulaDto dto){
				return channelWorldSchedulaMapper.queryChannelWorldSchedulaCount(dto);
			}
			
			@Override
			public List< ? extends AbstractNumberDto> queryList(OpChannelWorldSchedulaDto dto){
				List<OpChannelWorldSchedulaDto> list = channelWorldSchedulaMapper.queryChannelWorldSchedulaForList(dto);
				for(OpChannelWorldSchedulaDto o:list){
					o.setWorldLink(urlPrefix + o.getWorldLink());
				}
				return list;
			}
			
		});
	}
	
	/**
	 * 更新 
	 * @throws Exception
	 */
	@Override
	public void updateChannelWorldSchedula(Integer id,Integer userId,Integer worldId,
			Integer channelId,Integer finish,Integer valid,Integer operatorId,Date schedulaDate)throws Exception{
		OpChannelWorldSchedulaDto dto = new OpChannelWorldSchedulaDto();
		Date now = new Date();
		dto.setChannelId(channelId);
		dto.setFinish(finish);
		dto.setModifyDate(now);
		dto.setId(id);
		dto.setValid(valid);
		dto.setWorldId(worldId);
		dto.setOperatorId(operatorId);
		dto.setSchedulaDate(schedulaDate);
		channelWorldSchedulaMapper.updateChannelWorldSchedula(dto);
	}
	
	/**
	 * 批量删除
	 * @param idsStr
	 * @throws Exception
	 */
	@Override
	public void delChannelWorldSchedula(String idsStr)throws Exception{
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		channelWorldSchedulaMapper.delChannelWorldSchedula(ids);
	}
	
	/**
	 * 添加
	 * @throws Exception
	 */
	@Override
	public void insertChannelWorldSchedula(Integer worldId,
			Integer channelId,Integer finish,Integer valid,Integer operatorId)throws Exception{
		OpChannelWorldSchedulaDto dto = new OpChannelWorldSchedulaDto();
		Date now = new Date();
		dto.setWorldId(worldId);
		long r = channelWorldSchedulaMapper.queryChannelWorldSchedulaCount(dto);
		dto.setChannelId(channelId);
		dto.setModifyDate(now);
		dto.setAddDate(now);
		dto.setSchedulaDate(now);
		dto.setFinish(finish);
		dto.setValid(valid);
		dto.setOperatorId(operatorId);
		if(0 == r){
			channelWorldSchedulaMapper.insertChannelWorldSchedula(dto);
		}
	}
	
	/**
	 * 批量添加
	 */
	public void batchAddChannelWorldSchedula(String[] wIds, Date schedula,Integer minuteTimeSpan,Integer channelId,Integer finish,
			Integer valid,Integer operatorId)throws Exception{
		Date now = new Date();
		long timeSpan = minuteTimeSpan*60*1000L;
		for(int i=0;i<wIds.length; i++){
			String s = wIds[i];
			if(s.equals(""))continue;
			Integer worldId = Integer.parseInt(s);
			
			OpChannelWorldSchedulaDto dto = new OpChannelWorldSchedulaDto();			
			dto.setWorldId(worldId);
			dto.setChannelId(channelId);
			long r = channelWorldSchedulaMapper.queryChannelWorldSchedulaCount(dto);
			
			dto.setModifyDate(now);
			dto.setAddDate(now);
			dto.setSchedulaDate(new Date(schedula.getTime()+timeSpan*i));
			dto.setFinish(finish);
			dto.setValid(valid);
			dto.setOperatorId(operatorId);
			
			if(0 == r){
				channelWorldSchedulaMapper.insertChannelWorldSchedula(dto);
			}else{
				channelWorldSchedulaMapper.updateChannelWorldSchedula(dto);
			}
		}
	}
	
	@Override
	public void channelWorldSchedula()throws Exception{
		Date begin = new Date();
		logger.info("频道织图计划开始。开始时间："+begin.toString());
		OpChannelWorldSchedulaDto dto = new OpChannelWorldSchedulaDto();
		dto.setModifyDate(begin);
		dto.setAddDate(new Date(begin.getTime() - workingTime));
		dto.setValid(Tag.TRUE);
		dto.setFinish(Tag.FALSE);
		
		// 获取出的集合是按照调度时间的正序来排列的，如：第一个数据为10:00，最后一个数据为10:50
		List<OpChannelWorldSchedulaDto> list = channelWorldSchedulaMapper.queryChannelWorldSchedula(dto);
		
		for (OpChannelWorldSchedulaDto schedulaDto : list) {
		    
		    /*
		     * 优先执行调度表的刷新，若下列更新频道表失败，则人为操作，重新设置有效性与加精
		     * 因为考虑到若频道表刷新执行失败，那么调度表不会被置为成功，每次都会执行，会加大服务器压力
		     */
		    updateChannelWorldSchedula(schedulaDto.getId(),null,null,null,Tag.TRUE,null,null,null);
		    
		    OpChannelWorld world = new OpChannelWorld();
		    world.setChannelId(schedulaDto.getChannelId());
		    world.setWorldId(schedulaDto.getWorldId());
		    world.setValid(schedulaDto.getValid());
		    channelService.updateChannelWorld(world);
		    
		    // 刷新频道织图的serial，让10:50的织图排在最新，即serial最大
		    channelService.addChannelWorldId(schedulaDto.getChannelId(), schedulaDto.getWorldId());
		    
        	    /*
        	     * 下列注释，是针对以前频道的玩法：一张织图添加到频道中，会推送消息给用户 现在频道玩法变化，故先注释
        	     */
//        	    try {
//        		channelService.addChannelWorldRecommendMsgByWorldId(schedulaDto.getWorldId());
//        	    } catch (Exception e) {
//        
//        	    }
		}
		
		//通知
		
		Date end = new Date();
		logger.info("频道织图计划结束。结束时间：" + end.toString() + ". 花费: " + (end.getTime() - begin.getTime()) + "ms.");
	}
	
	/**
	 * 重新排序
	 * @param wIds
	 * @param schedula
	 * @param operator
	 * @throws Exception
	 */
	@Override
	public void reSort(String[] ids,Date schedula,Integer minuteTimeSpan,Integer operator)throws Exception{
		long timeSpan = minuteTimeSpan*60*1000L;
		for(int i=  0;i < ids.length; i++){
			String idStr = ids[i];
			if(idStr != null && idStr != ""){
				int id = Integer.parseInt(ids[i]);
				long t = schedula.getTime() + i*timeSpan;//用以排序
				updateChannelWorldSchedula(id, null, null, null, null, null, operator, new Date(t));
			}
		}
	}
}
