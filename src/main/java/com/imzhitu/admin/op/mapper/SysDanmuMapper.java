package com.imzhitu.admin.op.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpSysDanmu;

public interface SysDanmuMapper {

	@DataSource("master")
	public void save(OpSysDanmu danmu);
	
	@DataSource("slave")
	public List<OpSysDanmu> querySysDanmu(OpSysDanmu danmu);
	
	@DataSource("slave")
	public long queryTotal(OpSysDanmu danmu);
	
	@DataSource("master")
	public void update(OpSysDanmu danmu);
	
	@DataSource("master")
	public void deleteByIds(Integer[] ids);
	
	@DataSource("master")
	public void updateSerialById(@Param("id")Integer id,
			@Param("serial")Integer serial);
	
	@DataSource("slave")
	public OpSysDanmu queryById(Integer id);
}
