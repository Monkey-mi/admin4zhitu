package com.imzhitu.admin.ztworld.dao;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.HTWorldTypeWorldSchedulaDto;

import java.util.List;
import java.util.Date;
import java.util.Map;

public interface HTWorldTypeWorldSchedulaDao extends BaseDao{
	public void addTypeWorldSchedula(HTWorldTypeWorldSchedulaDto dto);
	public void delTypeWorldSchedulaByIds(Integer[] ids);
	public long queryTypeWorldSchedulaCountByMaxId(Map<String , Object> attr);
	public long queryTypeWorldSchedulaCountByMaxId(Date maxSchedula,Map<String , Object> attr);
	public List<HTWorldTypeWorldSchedulaDto> queryTypeWorldSchedula(Date maxSchedula,Map<String , Object> attr,RowSelection rowSelection);
	public List<HTWorldTypeWorldSchedulaDto> queryTypeWorldSchedula(Map<String , Object> attr,RowSelection rowSelection);
	public void updateCompleteByIds(Integer[] ids,Integer complete);
	public List<Integer> getWorldIdBySchedula(Date begin,Date end);
	public boolean checkIsExistByWid(Integer wid);
	public void updateTypeWorldSchedula(Integer world_id,Date schedula,Date modifyDate,Integer operatorId,Integer complete);
}
