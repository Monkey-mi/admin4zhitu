package com.imzhitu.admin.op.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.pojo.OpMsgBulletin;

/**
 * 
 * @author zxx
 *
 */
public interface OpMsgBulletinMapper {
	/**
	 * 添加
	 * @param dto
	 */
	public void insertMsgBulletin(OpMsgBulletin dto);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void batchDeleteMsgBulletin(Integer[] ids);
	
	/**
	 * 更新
	 * @param dto
	 */
	public void updateMsgBulletin(OpMsgBulletin dto);
	
	/**
	 * 批量更新有效性
	 * @param ids
	 */
	public void batchUpdateMsgBulletinValid(@Param("ids")Integer[] ids,@Param("valid")Integer valid,@Param("operator")Integer operator);
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	public List<OpMsgBulletin> queryMsgBulletin(OpMsgBulletin dto);
	
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	public long queryMsgBulletinTotalCount(OpMsgBulletin dto);
	
	/**
	 * 根据ids 来查询
	 * @param ids
	 * @return
	 */
	public List<OpMsgBulletin> queryMsgBulletinByIds(@Param("ids")Integer[] ids);
}
