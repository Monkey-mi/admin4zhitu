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
	 * 根据分类进行分页查询
	 * 
	 * @param category	公告分类，为null时，查询全部分类的公告
	 * @param firstRow	分页起始位置，为null时，不进行分页查询
	 * @param limit		分页数量，为null时，不进行分页查询
	 * @return
	 * @modify zhangbo	2015年12月19日
	 */
	@DataSource("slave")
	public List<OpMsgBulletin> queryMsgBulletin(@Param("category") Integer category, @Param("firstRow") Integer firstRow, @Param("limit") Integer limit);

	/**
	 * 根据公告分类查询总数
	 * 
	 * @param category	公告分类，为null时为查询全部总数
	 * @return
	 */
	@DataSource("slave")
	public long queryMsgBulletinTotalCount(@Param("category") Integer category);

	/**
	 * 根据ids 来查询
	 * @param ids
	 * @return
	 */
	@DataSource("slave")
	public List<OpMsgBulletin> queryMsgBulletinByIds(@Param("ids") Integer[] ids);

	/**
	 * 根据id，得到公告对象
	 * 
	 * @param id	公告id
	 * @return
	 * @author zhangbo	2015年12月14日
	 */
	OpMsgBulletin getMsgBulletinById(@Param("id") Integer id);
	
	/**
	 * 旧方法移植，用于老的缓存功能刷新
	 * 
	 * @return
	 */
	@DataSource("slave")
	public List<OpMsgBulletin> queryMsgBulletinByType(OpMsgBulletin dto);
}
