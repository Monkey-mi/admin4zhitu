package com.imzhitu.admin.ztworld.service.impl;

import java.util.Map;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.ztworld.service.ZTWorldTypeWorldSchedulaService;
import com.imzhitu.admin.ztworld.dao.HTWorldTypeWorldSchedulaDao;
import com.imzhitu.admin.common.pojo.HTWorldTypeWorldSchedulaDto;










import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ZTWorldTypeWorldSchedulaServiceImpl extends BaseServiceImpl implements ZTWorldTypeWorldSchedulaService{
	
	@Autowired
	private HTWorldTypeWorldSchedulaDao worldTypeWorldSchedulaDao;
	
	
	@Override
	public void queryTypeWorldSchedula(Date maxSchedula,Integer wid,Integer timeType,Date beginTime,Date endTime,int page, int rows, Map<String, Object> jsonMap)throws Exception{
		Map<String,Object> attr = new HashMap<String,Object>();
		long totalCount=0l;
		List<HTWorldTypeWorldSchedulaDto> list = null;
		
		if(null != wid)
			attr.put("worldId", wid);
		if(null != timeType)
			attr.put("timeType", timeType);
		if(null != beginTime)
			attr.put("beginTime", beginTime);
		if(null != endTime)
			attr.put("endTime", endTime);
		
		if(maxSchedula==null){
			totalCount = worldTypeWorldSchedulaDao.queryTypeWorldSchedulaCountByMaxId(attr);
			list = worldTypeWorldSchedulaDao.queryTypeWorldSchedula(attr,new RowSelection(page,rows));
			if(list.size()>0){
				HTWorldTypeWorldSchedulaDto dto = list.get(0);
				maxSchedula = dto.getSchedula();
			}
		}else{
			totalCount = worldTypeWorldSchedulaDao.queryTypeWorldSchedulaCountByMaxId(maxSchedula,attr);
			list = worldTypeWorldSchedulaDao.queryTypeWorldSchedula(maxSchedula,attr,new RowSelection(page,rows));
		}
		jsonMap.put("maxSchedula",maxSchedula);
		jsonMap.put("total", totalCount);
		jsonMap.put("rows",list);
	}
	
	@Override
	public void addTypeWorldSchedula(Integer world_id,Date schedula, Integer operatorId,Integer complete)throws Exception{
		Date now =new Date();
		boolean r = worldTypeWorldSchedulaDao.checkIsExistByWid(world_id);
		if(r==false)
			worldTypeWorldSchedulaDao.addTypeWorldSchedula(new HTWorldTypeWorldSchedulaDto(world_id,schedula,now,now,operatorId,null,complete));
		else
			worldTypeWorldSchedulaDao.updateTypeWorldSchedula(world_id,schedula,now,operatorId,complete);
	}
	
	@Override
	public void delTypeWorldSchedulaByIds(String idStr)throws Exception{
		Integer[] ids = StringUtil.convertStringToIds(idStr);
		worldTypeWorldSchedulaDao.delTypeWorldSchedulaByIds(ids);
	}
	@Override
	public void updateCompleteByWorldIds(Integer[] worldIds,Integer complete)throws Exception{
		worldTypeWorldSchedulaDao.updateCompleteByWorldIds(worldIds, complete);
	}
	
	@Override
	public List<Integer> getWorldIdBySchedula(Date begin,Date end)throws Exception{
		return worldTypeWorldSchedulaDao.getWorldIdBySchedula(begin, end);
	}
	
	@Override
	public void reSort(String[] wids,Date schedula,Integer operatorId)throws Exception {
		Date now = new Date();
		for(int i= wids.length -1;i >= 0; i--){
			String wid = wids[i];
			if(wid != null && wid != ""){
				int worldId = Integer.parseInt(wids[i]);
				long t = schedula.getTime() - i*1000;//用以排序
				worldTypeWorldSchedulaDao.updateTypeWorldSchedula(worldId, new Date(t), now, operatorId, Tag.FALSE);
			}
		}
	}
}
