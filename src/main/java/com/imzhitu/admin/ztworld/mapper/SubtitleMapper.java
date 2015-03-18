package com.imzhitu.admin.ztworld.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hts.web.common.pojo.HTWorldSubtitleDto;
import com.imzhitu.admin.common.pojo.ZTWorldSubtitle;

public interface SubtitleMapper {

	public List<HTWorldSubtitleDto> queryCacheSubtitle(@Param("limit")Integer limit);
	
	public List<ZTWorldSubtitle> queryTitles(ZTWorldSubtitle title);
	
	public long queryTotal(ZTWorldSubtitle title);
	
	public void saveSubtitle(ZTWorldSubtitle title);
	
	public void update(ZTWorldSubtitle title);

	public void deleteByIds(Integer[] ids);
	
	public void updateSerialById(Integer id);
	
	public ZTWorldSubtitle queryById(Integer id);

}
