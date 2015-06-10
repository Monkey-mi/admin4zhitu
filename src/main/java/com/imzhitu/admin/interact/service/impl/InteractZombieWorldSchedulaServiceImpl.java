package com.imzhitu.admin.interact.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.ZombieWorldSchedulaDto;
import com.imzhitu.admin.interact.mapper.InteractZombieWorldSchedulaMapper;
import com.imzhitu.admin.interact.service.InteractZombieService;
import com.imzhitu.admin.interact.service.InteractZombieWorldSchedulaService;

public class InteractZombieWorldSchedulaServiceImpl extends BaseServiceImpl implements InteractZombieWorldSchedulaService{

	@Autowired
	private InteractZombieWorldSchedulaMapper zombieWorldSchedulaMapper;
	
	@Autowired
	private InteractZombieService zombieService;
		
	private Logger log = Logger.getLogger(InteractZombieWorldSchedulaService.class);
	
	public static long ZOMBIE_WORLD_SCHEDULA_TIME_SPAN = 4*60*60*1000L;
	
	@Override
	public void insertZombieWorldSchedula(Integer zombieWorldId, Date schedula,
			Integer operator) throws Exception {
		// TODO Auto-generated method stub
		ZombieWorldSchedulaDto dto = new ZombieWorldSchedulaDto();
		Date now = new Date();
		dto.setZombieWorldId(zombieWorldId);
		long total = zombieWorldSchedulaMapper.queryZombieWorldSchedulaTotalCount(dto);
		
		dto.setSchedula(schedula);
		dto.setAddDate(now);
		dto.setOperator(operator);
		dto.setValid(Tag.TRUE);
		dto.setFinished(Tag.FALSE);
		
		if(total == 0){
			zombieWorldSchedulaMapper.insertZombieWorldSchedula(dto);
		}else{
			zombieWorldSchedulaMapper.updateZombieWorldSchedula(dto);
		}
		
	}

	@Override
	public void batchDeleteZombieWorldSchedula(String idsStr) throws Exception {
		// TODO Auto-generated method stub
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		zombieWorldSchedulaMapper.batchDeleteZombieWorldSchedula(ids);
	}

	@Override
	public void updateZombieWorldSchedula(Integer id, Integer zombieWorldId,Integer valid,
			Integer finished, Date schedula,Integer operator) throws Exception {
		// TODO Auto-generated method stub
		ZombieWorldSchedulaDto dto = new ZombieWorldSchedulaDto();
		if (id == null && zombieWorldId == null) {
			throw new Exception("id或者zombieWorldId不能同时为空");
		}
		
		dto.setId(id);
		dto.setZombieWorldId(zombieWorldId);
		dto.setValid(valid);
		dto.setFinished(finished);
		dto.setSchedula(schedula);
		dto.setOperator(operator);
		zombieWorldSchedulaMapper.updateZombieWorldSchedula(dto);
	}

	@Override
	public void queryZombieWorldSchedula(Integer id, Integer valid,
			Integer finished, Integer zombieWorldId, Integer maxId, int page,
			int rows, Map<String, Object> jsonMap) throws Exception {
		// TODO Auto-generated method stub
		ZombieWorldSchedulaDto dto = new ZombieWorldSchedulaDto();
		
		dto.setId(id);
		dto.setZombieWorldId(zombieWorldId);
		dto.setValid(valid);
		dto.setFinished(finished);
		dto.setMaxId(maxId);
		dto.setFirstRow((page-1)*rows);
		dto.setLimit(rows);
		long total = zombieWorldSchedulaMapper.queryZombieWorldSchedulaTotalCount(dto);
		List<ZombieWorldSchedulaDto> list = null;
		Integer reMaxId = 0;
		if(total > 0){
			list = zombieWorldSchedulaMapper.queryZombieWorldSchedula(dto);
			reMaxId = list.get(0).getId();
		}
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, reMaxId);
	}

	@Override
	public long queryZombieWorldSchedulaTotalCount(Integer id, Integer valid,
			Integer finished, Integer zombieWorldId, Integer maxId)
			throws Exception {
		// TODO Auto-generated method stub
		ZombieWorldSchedulaDto dto = new ZombieWorldSchedulaDto();
		
		dto.setId(id);
		dto.setZombieWorldId(zombieWorldId);
		dto.setValid(valid);
		dto.setFinished(finished);
		dto.setMaxId(maxId);
		return zombieWorldSchedulaMapper.queryZombieWorldSchedulaTotalCount(dto);
	}

	@Override
	public List<ZombieWorldSchedulaDto> queryZombieWorldSchedulaByTime(
			Date beginDate, Date endDate,Integer valid,Integer finished) throws Exception {
		// TODO Auto-generated method stub
		return zombieWorldSchedulaMapper.queryZombieWorldSchedulaByTime(beginDate, endDate, valid, finished);
	}
	
	/**
	 * 自动马甲发图job
	 * @throws Exception
	 */
	@Override
	public void doZombieWorldSchedulaJob(){
		Date begin = new Date();
		log.info("doZombieWorldSchedulaJob begin:"+begin);
		Date beginDate = new Date(begin.getTime()-ZOMBIE_WORLD_SCHEDULA_TIME_SPAN);
		Date endDate = begin;
		Integer successCount = 0;
		Integer totalCount = 0 ;
		List<ZombieWorldSchedulaDto> list = null;
		try{
			list = zombieWorldSchedulaMapper.queryZombieWorldSchedulaByTime(beginDate, endDate, Tag.TRUE, Tag.FALSE);
		}catch(Exception e){
			log.info(e.getMessage());
		}
		 
		if(list != null && list.size() > 0){
			totalCount = list.size();
			for(ZombieWorldSchedulaDto o:list){
				try{
					//zombieService.saveZombieWorldToHtWorld(o.getZombieWorldId());
					zombieService.saveZombieWorldToChannelAndWorld(o.getZombieWorldId());
					updateZombieWorldSchedula(o.getId(),null,null,Tag.TRUE,null,0);
					successCount++;
				}catch(Exception e){
					log.info(e.getMessage());
				}
			}
		}
		Date end = new Date();
		log.info("doZombieWorldSchedulaJob end. cost:"+(end.getTime()-begin.getTime())+"ms. total:"+totalCount+" . success:"+successCount);
	}
	
	/**
	 * 批量插入马甲发图计划
	 * @param zombieWorldIdsStr
	 * @param schedula
	 * @param minuteTimeSpan
	 * @param operactor
	 * @throws Exception
	 */
	@Override
	public void batchInsertZombieWorldSchedual(String zombieWorldIdsStr,Date schedula,Integer minuteTimeSpan,Integer operactor)throws Exception{
		Integer[] zombieWorldIds = StringUtil.convertStringToIds(zombieWorldIdsStr);
		long timeSpan = minuteTimeSpan*60*1000L;
		for(int i = 0; i < zombieWorldIds.length; i++){
			insertZombieWorldSchedula(zombieWorldIds[i],new Date(schedula.getTime() + i*timeSpan),operactor);
		}
	}

}
