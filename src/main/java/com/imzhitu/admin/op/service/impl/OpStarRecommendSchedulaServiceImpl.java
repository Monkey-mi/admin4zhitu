package com.imzhitu.admin.op.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.common.pojo.OpStarRecommendDto;
import com.imzhitu.admin.common.pojo.OpStarRecommendSchedulaDto;
import com.imzhitu.admin.op.mapper.OpStarRecommendMapper;
import com.imzhitu.admin.op.mapper.OpStarRecommendSchedulaMapper;
import com.imzhitu.admin.op.service.OpStarRecommendSchedulaService;

public class OpStarRecommendSchedulaServiceImpl extends BaseServiceImpl implements OpStarRecommendSchedulaService{

	@Autowired
	private OpStarRecommendSchedulaMapper starRecommendSchedulaMapper;
	private static long doStarRecommendSchedulaTimeSpan = 11*60*1000;//10分钟，因为读写分离的问题，就顺延1分钟
	private Logger log = Logger.getLogger(OpStarRecommendSchedulaServiceImpl.class);
	
	@Autowired
	private OpStarRecommendMapper starRecommendMapper;
	
	@Override
	public void insertStarRecommendSchedula(Integer osrId, Integer userId,
			Date schedula, Integer operator, Integer valid,Integer top) throws Exception {
		// TODO Auto-generated method stub
		OpStarRecommendSchedulaDto dto = new OpStarRecommendSchedulaDto();
		dto.setOsrId(osrId);
		dto.setTop(top);
		long totalCount = starRecommendSchedulaMapper.queryStarRecommendSchedulaTotalCount(dto);
		
		
		dto.setSchedula(schedula);
		dto.setOperator(operator);
		dto.setValid(valid);
		Date now = new Date();
		dto.setAddDate(now);
		dto.setModifyDate(now);
		dto.setFinish(Tag.FALSE);
		if(totalCount != 0){
			starRecommendSchedulaMapper.updateStarRecommendSchedula(dto);
		}else{
			starRecommendSchedulaMapper.insertStarRecommendSchedula(dto);
		}
		
		
	}

	@Override
	public void deleteStarRecommendSchedula(Integer id, Integer osrId,
			Integer userId, Integer valid) throws Exception {
		// TODO Auto-generated method stub
		OpStarRecommendSchedulaDto dto = new OpStarRecommendSchedulaDto();
		dto.setOsrId(osrId);
		dto.setId(id);
		dto.setValid(valid);
		starRecommendSchedulaMapper.deleteStarRecommendSchedula(dto);
	}

	@Override
	public void updateStarRecommendSchedula(Integer id, Integer osrId,
			Integer userId, Integer valid, Date schedula, Integer operator,Integer finish)
			throws Exception {
		// TODO Auto-generated method stub
		OpStarRecommendSchedulaDto dto = new OpStarRecommendSchedulaDto();
		dto.setOsrId(osrId);
		dto.setSchedula(schedula);
		dto.setOperator(operator);
		dto.setValid(valid);
		dto.setId(id);
		dto.setFinish(finish);
		Date now = new Date();
		dto.setModifyDate(now);
		starRecommendSchedulaMapper.updateStarRecommendSchedula(dto);
	}

	@Override
	public void queryStarRecommendSchedula(Integer maxId, int page, int rows,
			Map<String, Object> jsonMap, Integer id, Integer osrId,
			Integer userId, Integer valid, Date addDate, Date modifyDate,Integer top,Integer finish)
			throws Exception {
		// TODO Auto-generated method stub
		OpStarRecommendSchedulaDto dto = new OpStarRecommendSchedulaDto();
		dto.setMaxId(maxId);
		dto.setId(id);
		dto.setOsrId(osrId);
		dto.setUserId(userId);
		dto.setValid(valid);
		dto.setAddDate(addDate);
		dto.setModifyDate(modifyDate);
		dto.setTop(top);
		dto.setFinish(finish);
		buildNumberDtos(dto,page,rows,jsonMap,new NumberDtoListAdapter<OpStarRecommendSchedulaDto>(){
			@Override
			public long queryTotal(OpStarRecommendSchedulaDto dto){
				return starRecommendSchedulaMapper.queryStarRecommendSchedulaTotalCount(dto);
			}
			
			@Override
			public List< ? extends AbstractNumberDto> queryList(OpStarRecommendSchedulaDto dto){
				return starRecommendSchedulaMapper.queryStarRecommendSchedula(dto);
			}
		});
	}

	@Override
	public long queryStarRecommendSchedulaTotalCount(Integer maxId, Integer id,
			Integer osrId, Integer userId, Integer valid, Date addDate,
			Date modifyDate,Integer top,Integer finish) throws Exception {
		// TODO Auto-generated method stub
		OpStarRecommendSchedulaDto dto = new OpStarRecommendSchedulaDto();
		dto.setOsrId(osrId);
		dto.setUserId(userId);
		dto.setValid(valid);
		dto.setId(id);
		dto.setAddDate(addDate);
		dto.setModifyDate(modifyDate);
		dto.setMaxId(maxId);
		dto.setTop(top);
		dto.setFinish(finish);
		return starRecommendSchedulaMapper.queryStarRecommendSchedulaTotalCount(dto);
	}
	
	/**
	 * 重新排序计划
	 * @param wIds
	 * @param schedula
	 * @param operator
	 * @throws Exception
	 */
	@Override
	public void reSort(String[] osrIds,Date schedula,Integer operator,Integer timeMinute)throws Exception{
		for(int i= osrIds.length -1;i >= 0; i--){
			String osrIdStr = osrIds[i];
			if(osrIdStr != null && osrIdStr != ""){
				int osrId = Integer.parseInt(osrIds[i]);
				long t = schedula.getTime() - i*1000;//用以排序
				insertStarRecommendSchedula(osrId,null,new Date(t),operator,Tag.TRUE,Tag.TRUE);
				insertStarRecommendSchedula(osrId,null,new Date(t+timeMinute*60*1000),operator,Tag.TRUE,Tag.FALSE);
			}
		}
	}
	
	/**
	 * 执行达人推荐置顶计划
	 */
	public void doStarRecommendSchedula(){
		Date now = new Date();
		Integer resultCount = 0;
		log.info("doStarRecommendSchedula begin:"+now);
		OpStarRecommendSchedulaDto dto = new OpStarRecommendSchedulaDto();
		dto.setFinish(Tag.FALSE);		
		dto.setModifyDate(now);
		dto.setAddDate(new Date(now.getTime() - doStarRecommendSchedulaTimeSpan));
		dto.setValid(Tag.TRUE);
		List<OpStarRecommendSchedulaDto> list = starRecommendSchedulaMapper.queryStarRecommendSchedula(dto);
		OpStarRecommendDto starRecommend = new OpStarRecommendDto();
		
		for(OpStarRecommendSchedulaDto o:list){
			starRecommend.setId(o.getOsrId());
			starRecommend.setTop(o.getTop());
			starRecommendMapper.updateStarRecommend(starRecommend);
			o.setFinish(Tag.TRUE);
			starRecommendSchedulaMapper.updateStarRecommendSchedulaFinish(o);;
			resultCount++;
		}
		Date end = new Date();
		log.info("doStarRecommendSchedula end. cost:"+(end.getTime() - now.getTime()) + "ms. total:"+list.size()+". success:"+resultCount);
	}

}
