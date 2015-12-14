package com.imzhitu.admin.op.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
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
	@DataSource("master")
	public void insertMsgBulletin(OpMsgBulletin dto);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@DataSource("master")
	public void batchDeleteMsgBulletin(Integer[] ids);
	
	/**
	 * 更新
	 * @param dto
	 */
	@DataSource("master")
	public void updateMsgBulletin(OpMsgBulletin dto);
	
	/**
	 * 批量更新有效性
	 * @param ids
	 */
	@DataSource("master")
	public void batchUpdateMsgBulletinValid(@Param("ids")Integer[] ids,@Param("valid")Integer valid,@Param("operator")Integer operator);
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<OpMsgBulletin> queryMsgBulletin(OpMsgBulletin dto);
	
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryMsgBulletinTotalCount(OpMsgBulletin dto);
	
	/**
	 * 根据ids 来查询
	 * @param ids
	 * @return
	 */
	@DataSource("slave")
	public List<OpMsgBulletin> queryMsgBulletinByIds(@Param("ids")Integer[] ids);
	
	/**
	 * 根据id，得到公告对象
	 * 
	 * @param id	公告id
	 * @return
	 * @author zhangbo	2015年12月14日
	 */
	OpMsgBulletin getMsgBulletinById(@Param("id")Integer id);
}
