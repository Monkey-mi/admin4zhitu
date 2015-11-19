package com.imzhitu.admin.ztworld.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.UserMsgAtWorldDto;

/**
 * 织图描述中被@人操作
 * 
 * @author zhangbo	2015年11月2日
 *
 */
public interface UserMsgAtWorldMapper {
	/**
	 * 查询数据
	 * @param worldId
	 * @return 
		*	2015年11月11日
		*	mishengliang
	 */
	@DataSource("slave")
	public List<UserMsgAtWorldDto> queryAtWorldByWorldId(@Param("worldId") Integer worldId);
}
