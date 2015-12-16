package com.imzhitu.admin.op.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpMsgBulletin;
import com.imzhitu.admin.op.pojo.NearBulletinDto;

/**
 * 附近公告数据访问接口
 * 
 * @author lynch 2015-12-15
 *
 */
public interface OpMsgNearBulletinMapper {
	
	/**
	 * 添加公告
	 * 
	 * @param bulletin
	 * @author lynch 2015-12-15
	 */
	@DataSource("master")
	public void save(OpMsgBulletin bulletin);
	
	/**
	 * 根据id删除
	 * 
	 * @param id
	 * @author lynch 2015-12-15
	 */
	@DataSource("master")
	public void delById(@Param("id")Integer id);
	
	/**
	 * 更新
	 * 
	 * @param dto
	 * @author lynch 2015-12-15
	 */
	@DataSource("master")
	public void update(OpMsgBulletin dto);
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<NearBulletinDto> queryList(OpMsgBulletin dto);
	
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryCount(OpMsgBulletin dto);
	
	/**
	 * 根据id查询公告对象
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public NearBulletinDto queryById(@Param("id")Integer id);
}
