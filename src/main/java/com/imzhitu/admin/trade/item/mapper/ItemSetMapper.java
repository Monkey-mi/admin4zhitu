package com.imzhitu.admin.trade.item.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.trade.item.pojo.ItemSet;

/**
 * 商品集合数据操作类
 * 
 * @author zhangbo	2015年12月8日
 *
 */
public interface ItemSetMapper {
	
	/**
	 * 新增商品集合
	 * 
	 * @param id			商品集合id
	 * @param title			商品集合标题
	 * @param description	商品集合描述
	 * @param path			商品集合图片路径
	 * @param thumb			商品集合缩略图路径
	 * @param type			商品集合链接类型
	 * @param link			商品集合链接内容
	 * @param operator		操作者id，即管理员id
	 * @param serial		序号，序号越大排序越靠前
	 * 
	 * @author zhangbo	2015年12月9日
	 */
	@DataSource("master")
	void insert(@Param("id")Integer id, @Param("title")String title, @Param("description")String description, @Param("path")String path, @Param("thumb")String thumb, 
			@Param("type")Integer type, @Param("link")String link, @Param("operator")Integer operator, @Param("serial")Integer serial);

	/**
	 * 根据id更新商品集合
	 * 
	 * @param id			商品集合主键id
	 * @param title			商品集合标题
	 * @param description	商品集合描述
	 * @param path			商品集合图片路径
	 * @param thumb			商品集合缩略图路径
	 * @param type			商品集合链接类型
	 * @param link			商品集合链接内容
	 * @param operator		操作者id，即管理员id
	 * @param serial		序号，序号越大排序越靠前
	 * 
	 * @author zhangbo	2015年12月9日
	 */
	@DataSource("master")
	void update(@Param("id")Integer id, @Param("title")String title, @Param("description")String description, @Param("path")String path, @Param("thumb")String thumb, @Param("link")String link, @Param("operator")Integer operator);

	/**
	 * 根据id删除商品集合
	 * 
	 * @param id	商品集合主键id
	 * 
	 * @author zhangbo	2015年12月9日
	 */
	@DataSource("master")
	void deleteById(@Param("id")Integer id);

	/**
	 * 分页查询商品集合
	 * 
	 * @param fristRow	分页查询起始位置
	 * @param limit		分页查询每页数量
	 * @return List<ItemSet>	商品集合列表 
	 * 
	 * @author zhangbo	2015年12月9日
	 */
	@DataSource("slave")
	List<ItemSet> queryItemSetList(@Param("firstRow")Integer firstRow, @Param("limit")Integer limit);

	/**
	 * 查询商品集合总数
	 * 
	 * @return	total	商品集合总数
	 * @author zhangbo	2015年12月9日
	 */
	@DataSource("slave")
	Integer queryItemSetTotal();
	
	/**
	 * 根据id，获取商品集合对象
	 * 
	 * @param id	商品集合id
	 * @return
	 * @author zhangbo	2015年12月11日
	 */
	@DataSource("slave")
	ItemSet getItemSetById(@Param("id")Integer id);

	/**
	 * 更新序号
	 * 
	 * @param id		商家集合id
	 * @param serial	序号
	 * @author zhangbo	2015年12月12日
	 */
	@DataSource("master")
	void updateSerial(@Param("id")Integer id, @Param("serial")Integer serial);

	/**
	 * 分页查询商品集合列表，并且结果集不包含传递过去的商品id
	 * 
	 * @param ids		商品集合ids
	 * @param fristRow	分页查询起始位置
	 * @param limit		分页查询每页数量
	 * @return
	 * @author zhangbo	2015年12月12日
	 */
	@DataSource("slave")
	List<ItemSet> queryItemSetListNotInIds(@Param("ids")Integer[] ids, @Param("firstRow")Integer firstRow, @Param("limit")Integer limit);

	/**
	 * 分页查询商品集合列表，根据标题查询
	 * 
	 * @param fristRow
	 * @param limit
	 * @return
	 * @author zhangbo	2015年12月21日
	 */
	@DataSource("slave")
	List<ItemSet> queryItemSetListByTitle(@Param("title")String idOrName, @Param("firstRow")Integer fristRow, @Param("limit")Integer limit);
	
}
