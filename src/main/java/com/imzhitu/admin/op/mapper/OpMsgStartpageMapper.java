package com.imzhitu.admin.op.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpMsgStartpage;

/**
 * 
 * @author zxx
 *
 */
public interface OpMsgStartpageMapper {
	/**
	 * 添加
	 * @param dto
	 */
	@DataSource("master")
	public void insertMsgStartpage(OpMsgStartpage dto);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@DataSource("master")
	public void batchDeleteMsgStartpage(Integer[] ids);
	
	/**
	 * 更新
	 * @param dto
	 */
	@DataSource("master")
	public void updateMsgStartpage(OpMsgStartpage dto);
	
	/**
	 * 批量更新有效性
	 * @param ids
	 */
	@DataSource("master")
	public void batchUpdateMsgStartpageValid(@Param("ids")Integer[] ids,@Param("valid")Integer valid,@Param("operator")Integer operator);
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<OpMsgStartpage> queryMsgStartpage(OpMsgStartpage dto);
	
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryMsgStartpageTotalCount(OpMsgStartpage dto);
	
	/**
	 * 根据ids 来查询
	 * @param ids
	 * @return
	 */
	@DataSource("slave")
	public List<OpMsgStartpage> queryMsgBulletinByIds(@Param("ids")Integer[] ids);
	
	/**
	 * 批量更新lastmodifie
	 * @param ids
	 */
	@DataSource("master")
	public void batchUpdateMsgStartpageLastModified(Integer[] ids);
}
