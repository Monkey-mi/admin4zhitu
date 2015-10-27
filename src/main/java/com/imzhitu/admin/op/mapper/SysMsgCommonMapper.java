package com.imzhitu.admin.op.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hts.web.common.pojo.OpSysMsgDto;
import com.imzhitu.admin.common.pojo.OpSysMsg;

/**
 * <p>
 * 公用系统消息数据访问接口
 * </p>
 * 
 * @author lynch 2015-10-26
 *
 */
public interface SysMsgCommonMapper {

	public void saveCommonMsg(OpSysMsg msg);
	
	public List<OpSysMsgDto> queryCacheMsg(@Param("limit")Integer limit);
	
}
