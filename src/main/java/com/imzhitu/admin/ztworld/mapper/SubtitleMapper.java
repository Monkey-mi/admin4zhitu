package com.imzhitu.admin.ztworld.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hts.web.common.pojo.HTWorldSubtitleDto;
import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.ZTWorldSubtitle;

public interface SubtitleMapper {

	@DataSource("slave")
	public List<HTWorldSubtitleDto> queryCacheSubtitle(@Param("limit")Integer limit);
	
	@DataSource("slave")
	public List<ZTWorldSubtitle> queryTitles(ZTWorldSubtitle title);
	
	@DataSource("slave")
	public long queryTotal(ZTWorldSubtitle title);
	
	public void saveSubtitle(ZTWorldSubtitle title);
	
	public void update(ZTWorldSubtitle title);

	public void deleteByIds(Integer[] ids);
	
	public void updateSerialById(@Param("id")Integer id, 
			@Param("serial")Integer serial);
	
	@DataSource("slave")
	public ZTWorldSubtitle queryById(Integer id);

}
