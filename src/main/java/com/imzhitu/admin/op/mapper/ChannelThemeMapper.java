package com.imzhitu.admin.op.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.ChannelTheme;

public interface ChannelThemeMapper {

	/**
	 * 查询频道列表
	 * 
	 * @param channel
	 * @return
	 */
	@DataSource("slave")
	public List<ChannelTheme> queryAllThemeById(@Param("themeId")Integer themeId);
	
	/**
	 * 查询总数
	 * @return 
		*	2015年12月2日
		*	mishengliang
	 */
	@DataSource("slave")
	public Integer getTotal();
	
	/**
	 * 插入新的主题
	 * 
	 * @param themeName 
		*	2015年12月2日
		*	mishengliang
	 */
	@DataSource("master")
	public void insertChannelTheme(@Param("themeName")String themeName);
	
	
	/**
	 * 更新主题
	 * 
	 * @param themeId
	 * @param themeName 
		*	2015年12月2日
		*	mishengliang
	 */
	@DataSource("master")
	public void updateChannelTheme(@Param("themeId")Integer themeId,@Param("themeName")String themeName);
	
	
	/**
	 * 
	 * @param themeId 
		*	2015年12月2日
		*	mishengliang
	 */
	@DataSource("master")
	public void deleteChannelTheme(@Param("themeId")Integer themeId);
	
	
	/**
	 * 更新排序
	 *  
		*	2015年12月4日
		*	mishengliang
	 */
	public void updateChannelThemeSerial(@Param("id")Integer id,@Param("serial")Integer serial);
}
