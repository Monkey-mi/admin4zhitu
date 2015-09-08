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
	
	/**
	 * 查询最后一次发送的信息
	 * 查询条件（每个值可以联合或独立进行查询）：
	 * 1、senderId		发送人id（user表主键id）
	 * 2、recipientId	接收人id（user表主键id）
	 * 3、objType		消息对象类型
	 * 4、objId			消息对象id，包含织图id或接收人id（存放接收人id情况是通知选成红人）
	 * 5、objMeta		额外信息，根据不用的发送来传值查询，要视相关业务而定
	 * 6、objMeta2		额外信息2，根据不用的发送来传值查询，要视相关业务而定 
	 * 
	 * @param msg	系统通知对象
	 * @return	OpSysMsg	返回系统通知对象
	 * @author mishengliang	2015年8月31日
	 * @modify zhangbo 2015年9月7日
	 */
	@DataSource("slave")
	OpSysMsg getLastMsg(OpSysMsg msg);
}
