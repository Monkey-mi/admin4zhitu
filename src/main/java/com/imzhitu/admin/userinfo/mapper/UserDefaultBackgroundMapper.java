package com.imzhitu.admin.userinfo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.userinfo.pojo.DefaultBackground;

/**
 * @author zhangbo	2015年12月25日
 *
 */
public interface UserDefaultBackgroundMapper {
	
	@DataSource("slave")
	List<DefaultBackground> queryDefaultBackground();
	
	@DataSource("master")
	void deleteByIds(@Param("ids")Integer[] ids);

	@DataSource("master")
	void insert(@Param("background")String background);
}
