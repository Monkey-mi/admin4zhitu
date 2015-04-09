package com.imzhitu.admin.op.dao.impl;

import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.op.dao.SysMsgDao;

@Repository
public class SysMsgDaoImpl extends BaseDaoImpl implements SysMsgDao {

	public static String table = HTS.OPERATIONS_SYS_MSG;
	
	/**
	 * 批量保存消息
	 */
	private static final String BATCH_SAVE_MSG = "call func_batch_save_sys_msg(?,?,?,?,?)";

	/**
	 * 根据id删除私信 
	 */
	private static final String DELETE_BY_ID = "delete from " + table + " where id=?";
	
	
//	@Override
//	public void saveMsg(OpSysMsg msg) {
//		getMasterJdbcTemplate().update(SAVE_MSG, new Object[]{
//				msg.getId(),
//				msg.getSenderId(),
//				msg.getRecipientId(),
//				msg.getMsgDate(),
//				msg.getContent(),
//				msg.getObjType(),
//				msg.getObjId(),
//				msg.getObjMeta(),
//				msg.getThumbPath()
//		});
//	}
	

	@Override
	public void deleteMsgById(Integer id) {
		getMasterJdbcTemplate().update(DELETE_BY_ID, new Object[]{id});
	}

}
