package com.imzhitu.admin.trade.item;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.trade.item.service.ItemService;

/**
 * 商品控制类
 * 
 * @author zhangbo	2015年12月8日
 *
 */
@SuppressWarnings("serial")
public class ItemAction extends BaseCRUDAction {
	
	/**
	 * 商品主键id
	 * @author zhangbo	2015年12月9日
	 */
	private Integer id;
	
	/**
	 * 商品图片路径
	 * @author zhangbo	2015年12月9日
	 */
	private String imgPath;
	
	/**
	 * 商品缩略图路径
	 * @author zhangbo	2015年12月9日
	 */
	private String imgThumb;
	
	/**
	 * 商品名称
	 * @author zhangbo	2015年12月9日
	 */
	private String name;
	
	/**
	 * 商品简介
	 * @author zhangbo	2015年12月9日
	 */
	private String summary;
	
	/**
	 * 商品详情描述
	 * @author zhangbo	2015年12月9日
	 */
	private String description;
	
	/**
	 * 商品关联织图id
	 * @author zhangbo	2015年12月9日
	 */
	private Integer worldId;
	
	/**
	 * 商品价格
	 * @author zhangbo	2015年12月9日
	 */
	private BigDecimal price;
	
	/**
	 * 商品促销价
	 * @author zhangbo	2015年12月9日
	 */
	private BigDecimal sale;
	
	/**
	 * 商品销售量
	 * @author zhangbo	2015年12月9日
	 */
	private Integer sales;
	
	/**
	 * 商品库存量
	 * @author zhangbo	2015年12月9日
	 */
	private Integer stock;
	
	/**
	 * 淘宝商品真实id
	 * @author zhangbo	2015年12月9日
	 */
	private Integer trueItemId;
	
	/**
	 * 淘宝商品类型（1：淘宝，2：天猫）
	 * @author zhangbo	2015年12月9日
	 */
	private Integer trueItemType;
	
	/**
	 * 商品类别
	 * @author zhangbo	2015年12月9日
	 */
	private Integer categoryId;
	
	/**
	 * 商品品牌
	 * @author zhangbo	2015年12月9日
	 */
	private Integer brandId;
	
	/**
	 * 商品id集合，以逗号分隔
	 * @author zhangbo	2015年12月9日
	 */
	private String ids;
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * 保存商品
	 * 
	 * @return
	 * @author zhangbo	2015年12月9日
	 */
	public String saveitem() {
		try {
			if ( id == null ) {
				itemService.insertItem(name,imgPath,imgThumb,summary,description,worldId,price,sale,sales,stock,trueItemId,trueItemType,categoryId,brandId);
			} else {
				itemService.updateItem(id,name,imgPath,imgThumb,summary,description,worldId,price,sale,sales,stock,trueItemId,trueItemType,categoryId,brandId);
			}
			
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 构造商品列表
	 * 
	 * @return
	 * @author zhangbo	2015年12月9日
	 */
	public String buildItemList() {
		try {
			itemService.buildItemList(page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String batchDelete() {
		try {
			Integer[] idArray = StringUtil.convertStringToIds(ids);
			itemService.batchDelete(idArray);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

}
