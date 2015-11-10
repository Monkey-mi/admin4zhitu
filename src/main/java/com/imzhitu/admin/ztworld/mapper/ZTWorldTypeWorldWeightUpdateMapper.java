package com.imzhitu.admin.ztworld.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.ZTWorldTypeWorldWeightUpdateDto;


public interface ZTWorldTypeWorldWeightUpdateMapper {
	/**
	 * 添加
	 * @param dto
	 */
	@DataSource("master")
	public void saveTypeWorldWeight(ZTWorldTypeWorldWeightUpdateDto dto);
	
	
	/**
	 * 根据typeWorldId删除
	 * @param typeWorldId
	 */
	@DataSource("master")
	public void deleteTypeWorldWeightBytypeWorldId(@Param("typeWorldId")Integer typeWorldId);
	
	/**
	 * 查出typeWorldId是要将记录权重表中对应的weight改为0
	 * 同时利用typeWorldId删除此表中的记录
	 * @param endTime
	 * @return 
		*	2015年11月4日
		*	mishengliang
	 */
	@DataSource("slave")
	public List<ZTWorldTypeWorldWeightUpdateDto> selectTypeWorldWeightByEndTime(@Param("endTime")long endTime);
	
	
}
