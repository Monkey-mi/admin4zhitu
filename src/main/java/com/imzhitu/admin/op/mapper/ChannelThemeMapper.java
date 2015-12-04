package com.imzhitu.admin.op.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.ChannelTheme;

public interface ChannelThemeMapper {
	
	/**
	 * 查询频道主题列表
	 * 
	 * @return
	 * @author zhangbo	2015年12月4日
	 */
	List<ChannelTheme>	queryAllTheme();

	/**
	 * 查询总数
	 * 
	 * @return
	 * @author mishengliang	2015年12月2日
	 */
	@DataSource("slave")
	Integer getTotal();
	
	/**
	 * 插入新的主题
	 * 
	 * @return
	 * @author mishengliang	2015年12月2日
	 */
	@DataSource("master")
	void insertChannelTheme(@Param("themeName")String themeName);
	
	/**
	 * 更新主题
	 * 
	 * @return
	 * @author mishengliang	2015年12月2日
	 */
	@DataSource("master")
	void updateChannelTheme(@Param("themeId")Integer themeId,@Param("themeName")String themeName);
	
	/**
	 * 删除主题
	 * 
	 * @return
	 * @author mishengliang	2015年12月2日
	 */
	@DataSource("master")
	void deleteChannelTheme(@Param("themeId")Integer themeId);
	
	
	/**
	 * 更新排序
	 *  
		*	2015年12月4日
		*	mishengliang
	 */
	@DataSource("master")
	void updateChannelThemeSerial(@Param("id")Integer id,@Param("serial")Integer serial);
}
