package com.imzhitu.admin.op.mapper;

import org.apache.ibatis.annotations.Param;

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
	
	/**
	 * 查询发送的信息
	 * @param msg 
		*	2015年8月31日
		*	mishengliang
	 */
	@DataSource("slave")
	public OpSysMsg queryMsg(@Param("recipientId")Integer recipientId,@Param("objMeta2")String objMeta2);
}
