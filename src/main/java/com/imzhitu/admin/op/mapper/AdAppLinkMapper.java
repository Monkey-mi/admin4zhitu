package com.imzhitu.admin.op.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpAdAppLink;

/**
 * <p>
 * 广告换量链接数据访问接口
 * </p>
 * 
 * 创建时间: 2015-09-07
 * @author lynch
 *
 */
public interface AdAppLinkMapper {

	/**
	 * 查询链接列表
	 * 
	 * @param link
	 * @return
	 */
	@DataSource("slave")
	public List<OpAdAppLink> queryLink(OpAdAppLink link);
	
	/**
	 * 查询链接总数
	 * 
	 * @param link
	 * @return
	 */
	@DataSource("slave")
	public long queryLinkCount(OpAdAppLink link);
	
	/**
	 * 根据id查询链接
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public OpAdAppLink queryLinkById(Integer id);
	
	/**
	 * 更新链接
	 * 
	 * @param link
	 */
	@DataSource("master")
	public void update(OpAdAppLink link);
	
	/**
	 * 保存链接
	 * 
	 * @param link
	 */
	@DataSource("master")
	public void save(OpAdAppLink link);
	
	
	/**
	 * 根据ids批量删除记录
	 * 
	 * @param ids
	 */
	@DataSource("master")
	public void deleteByIds(Integer[] ids);
	
	/**
	 * 根据ids更新有效性
	 * 
	 * @param ids
	 * @param valid
	 */
	@DataSource("master")
	public void updateValidByIds(@Param("ids")Integer[] ids, @Param("valid")Integer valid);
	
	/**
	 * 更新序号
	 * 
	 * @param id
	 * @param serial
	 */
	@DataSource("master")
	public void updateSerialById(@Param("id")Integer id, @Param("serial")Integer serial);
}
