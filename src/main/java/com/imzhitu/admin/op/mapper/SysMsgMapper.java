package com.imzhitu.admin.op.mapper;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpSysMsg;

public interface SysMsgMapper {

	/**
	 * 通过存储过程批量保存系统消息
	 * @param msg
	 */
	@DataSource("master")
	public void saveMsgByProcedure(OpSysMsg msg);
	
	@DataSource("master")
	public void saveMsg(OpSysMsg msg);
	
}
